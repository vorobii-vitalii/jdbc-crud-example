package org.example.utils;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection createConnection();
}
