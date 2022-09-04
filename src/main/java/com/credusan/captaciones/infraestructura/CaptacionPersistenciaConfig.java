package com.credusan.captaciones.infraestructura;

import com.credusan.captaciones.dominio.puertos.PersistenciaCaptacion;
import com.credusan.captaciones.dominio.puertos.PersistenciaTipoCaptacion;
import com.credusan.captaciones.infraestructura.jdbc.daos.PersistenciaCaptacionJDBC;
import com.credusan.captaciones.infraestructura.jdbc.daos.PersistenciaTipoCaptacionJDBC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class CaptacionPersistenciaConfig {

    @Bean
    public PersistenciaTipoCaptacion getTipoCaptacionBean(DataSource dataSource) {
        return new PersistenciaTipoCaptacionJDBC(new JdbcTemplate(dataSource));
    }

    @Bean
    public PersistenciaCaptacion getCaptacionBean(DataSource dataSource) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("captacion").usingGeneratedKeyColumns("captid");
        return new PersistenciaCaptacionJDBC(new JdbcTemplate(dataSource), simpleJdbcInsert);
    }

}
