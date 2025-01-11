package ma.dentalSoft.service;

import ma.dentalSoft.dao.DashboardRepo;
import ma.dentalSoft.dao.implementations.DashboardRepoImpl;

public class DashboardService {
    private final DashboardRepo dashboardRepo = new DashboardRepoImpl();

    public String getWelcomeMessage(String username) {
        return dashboardRepo.getWelcomeMessage(username);
    }
}
