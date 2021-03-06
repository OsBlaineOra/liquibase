package liquibase.datatype;

import liquibase.util.StringUtils;

/**
 * This class represents a native data type used by a specific RDBMS. This is in contrast of
 * {@link LiquibaseDataType}, which represents data types used in changeSets (which will later be translated into
 * the RDBMS-specific data type if required).
 */
public class DatabaseDataType {

    private String type;

    public DatabaseDataType(String type) {
        this.type = type;
    }

    public DatabaseDataType(String name, Object... parameters) {
        if (parameters == null) {
            parameters = new Object[0];
        }
        this.type = name;

        String[] stringParams = new String[parameters.length];
        if (parameters.length > 0) {
            for (int i = 0; i<parameters.length; i++){
                if (parameters[i] == null) {
                    stringParams[i] = "NULL";
                } else {
                    stringParams[i] = parameters[i].toString();
                }
            }
            type += "("+ StringUtils.join(stringParams, ", ")+")";
        }
    }

    public void addAdditionalInformation(String additionalInformation) {
        if (additionalInformation != null) {
            this.type += " " + additionalInformation;
        }
    }

    /**
     * Mainly for postgres, check if the column is a serial data type.
     * @return Whether the type is serial
     */
    public boolean isAutoIncrement() {
        return "serial".equalsIgnoreCase(type) || "bigserial".equalsIgnoreCase(type) || "smallserial"
            .equalsIgnoreCase(type);
    }

    public String toSql() {
        return toString();
    }

    @Override
    public String toString() {
        return type;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
