public class RegistroAuditoriaBancaria 
        implements AutoCloseable {


    public void registrar(String mensaje){

        System.out.println(
            "AUDITORIA: " + mensaje
        );

    }


    @Override
    public void close(){

        System.out.println(
            "Registro cerrado correctamente"
        );

    }

}
