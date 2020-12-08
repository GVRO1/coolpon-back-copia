package br.com.coolpon.coolpon.config;

public class QueryBanco {
    public static final String USUARIO_POR_LOGIN = "SELECT email, password, active FROM business WHERE email = ?";

    public static final String PERMISSOES_POR_BUSINESS = "SELECT b.email as login, p.name as nome_permissao FROM permission_user up"
            + " JOIN business b ON b.id = up.business_id"
            + " JOIN permission p ON p.id = up.permission_id"
            + " WHERE b.email = ?";

}
