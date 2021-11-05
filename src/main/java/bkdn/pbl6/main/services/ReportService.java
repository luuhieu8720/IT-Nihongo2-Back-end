package bkdn.pbl6.main.services;

import java.util.ArrayList;

import bkdn.pbl6.main.configs.models.AccountModel;
import bkdn.pbl6.main.models.Report;

public interface ReportService {

	ArrayList<Report> getAll();

	Report get(String id) throws Exception;

	Boolean delete(String id, AccountModel accountModel) throws Exception;

	ArrayList<Report> find(Report report) throws Exception;

	Report newReport(Report report) throws Exception;

}
