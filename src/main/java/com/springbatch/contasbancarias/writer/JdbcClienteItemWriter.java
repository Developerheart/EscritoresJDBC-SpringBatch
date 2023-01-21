package com.springbatch.contasbancarias.writer;

import com.springbatch.contasbancarias.dominio.Cliente;
import com.springbatch.contasbancarias.dominio.Conta;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class JdbcClienteItemWriter {

    @Bean
    public JdbcBatchItemWriter<Conta> jdbcContaWriter(@Qualifier("appDataSource") DataSource appDataSouce) {
        return new JdbcBatchItemWriterBuilder<Conta>()
                .dataSource(appDataSouce)
                .sql("INSERT INTO conta (tipo, limite, cliente_id) values (?, ?, ?)")
                .itemPreparedStatementSetter(itemPSItemSetter())
                .build();
    }

    private ItemPreparedStatementSetter<Conta> itemPSItemSetter() {
        return new ItemPreparedStatementSetter<Conta>() {
            @Override
            public void setValues(Conta conta, PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, conta.getTipo().name());
                preparedStatement.setDouble(2, conta.getLimite());
                preparedStatement.setString(3, conta.getClienteId());
            }
        };
    }

}
