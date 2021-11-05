package bkdn.pbl6.main.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.entities.AccountEntity;
import bkdn.pbl6.main.entities.ReportEntity;
import bkdn.pbl6.main.enums.Role;
import bkdn.pbl6.main.models.Report;
import bkdn.pbl6.main.repositories.AccountRepository;
import bkdn.pbl6.main.repositories.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public ArrayList<Report> getAll() {
		ArrayList<ReportEntity> reportEntities = reportRepository.findAll();

		ArrayList<Report> reports = new ArrayList<>(reportEntities.size());
		for (ReportEntity reportEntity : reportEntities) {
			reports.add(new Report(reportEntity));
		}

		return reports;
	}

	@Override
	public Report get(String id) throws Exception {
		Optional<ReportEntity> optional = reportRepository.findById(id);
		if (optional.isEmpty())
			throw new Exception("Not found!");

		return new Report(optional.get());
	}

	@Override
	public Boolean delete(String id, AccountModel accountModel) throws Exception {
		Optional<ReportEntity> optional = reportRepository.findById(id);
		if (optional.isEmpty())
			return true;
		ReportEntity reportEntity = optional.get();

		Role role = new ArrayList<>(accountModel.getRole()).get(0).getRoleId();
		if (role == Role.Admin) {
			reportRepository.deleteById(id);
			return true;
		}
		if (role == Role.User && reportEntity.getIdUser().equals(accountModel.getAccount().getId())) {
			reportRepository.deleteById(id);
			return true;
		}

		throw new Exception("Unauthorized");
	}

	@Override
	public ArrayList<Report> find(Report report) throws Exception {
		ReportEntity exampleReport = new ReportEntity();

		if (!StringUtils.hasText(report.getUserName())) {
			AccountEntity userAccountEntity = accountRepository.findByUsername(report.getUserName());
			exampleReport.setIdUser(userAccountEntity.getId());
		}
		if (!StringUtils.hasText(report.getTutorName())) {
			AccountEntity tutorAccountEntity = accountRepository.findByUsername(report.getTutorName());
			exampleReport.setIdUser(tutorAccountEntity.getId());
		}

		ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(StringMatcher.EXACT)
				.withIgnorePaths("id", "score", "comment");

		Example<ReportEntity> example = Example.of(exampleReport, matcher);

		ArrayList<ReportEntity> reportEntities = new ArrayList<>(reportRepository.findAll(example));

		ArrayList<Report> reports = new ArrayList<>(reportEntities.size());
		for (ReportEntity reportEntity : reportEntities) {
			reports.add(new Report(reportEntity));
		}

		return reports;
	}

	@Override
	public Report newReport(Report report) throws Exception {
		AccountEntity userAccountEntity = accountRepository.findByUsername(report.getUserName());
		AccountEntity tutorAccountEntity = accountRepository.findByUsername(report.getTutorName());

		ReportEntity reportEntity = new ReportEntity(null, userAccountEntity.getId(), tutorAccountEntity.getId(),
				report.getScore(), report.getComment());

		reportEntity = reportRepository.save(reportEntity);

		return new Report(reportEntity);
	}

}
