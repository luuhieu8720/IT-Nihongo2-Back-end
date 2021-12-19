package bkdn.pbl6.main.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.TreeMap;

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

		return toModel(reportEntities);
	}

	@Override
	public Report get(String id) throws Exception {
		Optional<ReportEntity> optional = reportRepository.findById(id);
		if (optional.isEmpty())
			throw new Exception("Not found!");

		ReportEntity entity = optional.get();
		Report report = new Report(entity);

		String idUser = entity.getIdUser();
		AccountEntity userAccountEntity = accountRepository.findById(idUser).get();
		report.setUserName(userAccountEntity.getUsername());

		String idTutor = entity.getIdTutor();
		AccountEntity tutorAccountEntity = accountRepository.findById(idTutor).get();
		report.setTutorName(tutorAccountEntity.getUsername());

		return report;
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

		return toModel(reportEntities);
	}

	@Override
	public Report newReport(Report report) throws Exception {
		AccountEntity userAccountEntity = accountRepository.findByUsername(report.getUserName());
		AccountEntity tutorAccountEntity = accountRepository.findByUsername(report.getTutorName());

		ReportEntity reportEntity = new ReportEntity(null, userAccountEntity.getId(), tutorAccountEntity.getId(),
				report.getScore(), report.getComment());

		reportEntity = reportRepository.save(reportEntity);

		report.setId(reportEntity.getId());

		return report;
	}

	private ArrayList<Report> toModel(ArrayList<ReportEntity> entities) {
		TreeMap<String, AccountEntity> tree = new TreeMap<>();
		ArrayList<Report> models = new ArrayList<>(entities.size());
		for (ReportEntity entity : entities) {
			try {
				Report report = new Report(entity);

				String idUser = entity.getIdUser();
				if (tree.containsKey(idUser)) {
					report.setUserName(tree.get(idUser).getUsername());
				} else {
					AccountEntity accountEntity = accountRepository.findById(idUser).get();
					report.setUserName(accountEntity.getUsername());
					tree.put(idUser, accountEntity);
				}

				String idTutor = entity.getIdTutor();
				if (tree.containsKey(idTutor)) {
					report.setTutorName(tree.get(idTutor).getUsername());
				} else {
					AccountEntity accountEntity = accountRepository.findById(idTutor).get();
					report.setTutorName(accountEntity.getUsername());
					tree.put(idTutor, accountEntity);
				}

				models.add(report);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return models;
	}

}
