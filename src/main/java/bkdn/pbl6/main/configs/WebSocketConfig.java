package bkdn.pbl6.main.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import bkdn.pbl6.main.configs.services.AccountService;
import bkdn.pbl6.main.jwt.JwtTokenProvider;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AccountService accountService;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socket/endpoint").setAllowedOriginPatterns("*").withSockJS().setWebSocketEnabled(false)
				.setSessionCookieNeeded(false);
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue");
		registry.setApplicationDestinationPrefixes("/socket");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {

			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					String bearerToken = accessor.getFirstNativeHeader("Authorization");
					if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
						bearerToken = bearerToken.substring(7);
					} else {
						return message;
					}

					if (StringUtils.hasText(bearerToken) && tokenProvider.validateToken(bearerToken)) {
						String userId = tokenProvider.getStringFromJWT(bearerToken);
						UserDetails userDetails = accountService.loadUserById(userId);
						if (userDetails != null) {
							UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
									userDetails, null, userDetails.getAuthorities());
							accessor.setUser(authentication);
						}
					}
				}
				return message;
			}
		});
	}

}
