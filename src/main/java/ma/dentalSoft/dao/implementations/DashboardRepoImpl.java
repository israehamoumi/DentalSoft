package ma.dentalSoft.dao.implementations;

import ma.dentalSoft.dao.DashboardRepo;

public class DashboardRepoImpl implements DashboardRepo {

    @Override
    public String getWelcomeMessage(String username) {
        return "Welcome, " + username + "!";
    }
}
