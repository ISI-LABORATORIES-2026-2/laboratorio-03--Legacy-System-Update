public class Principal {


    public static void main(String[] args){


        CuentaBancaria cuenta =
                new CuentaCorriente(
                        "001",
                        "Samuel",
                        100,
                        500
                );


        try(RegistroAuditoriaBancaria auditoria =
                new RegistroAuditoriaBancaria()){


            cuenta.retirar(400);


            auditoria.registrar(
                "Retiro realizado. Saldo actual: "
                + cuenta.getSaldo()
            );


        }


    }

}
