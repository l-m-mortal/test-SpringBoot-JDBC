package com.example.testspringboot4jpajdbc;

import com.example.testspringboot4jpajdbc.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;


import javax.annotation.PostConstruct;
import java.util.Map;

@SpringBootApplication

public class TestSpringBoot4JdbcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBoot4JdbcApplication.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostConstruct
    public void initDB() {
        jdbcTemplate.update("create table if not exists Account ()");
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("INSERT INTO Account (id, name, email, bill) VALUES (1, 'Mike', 'Mike@email.xxx', 4000)");
        Account accountById = findAccountById(1l);
        System.out.println(accountById);
    }

    private Account findAccountById(Long accountId) {
        String query = "SELECT * FROM Account WHERE  id=%s";
        Map<String, Object> resultSet = jdbcTemplate.queryForMap(String.format(query, accountId));
        Account account = new Account();
        account.setId(accountId);
        account.setName((String)resultSet.get("name"));
        account.setEmail((String)resultSet.get("email"));
        account.setBill((Integer)resultSet.get("bill"));
        return  account;
    }
}
