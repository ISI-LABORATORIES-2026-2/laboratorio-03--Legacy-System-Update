public class CuentaAhorros extends CuentaBancaria {


    private double tasaInteres;


    public CuentaAhorros(String numeroCuenta,
                         String titular,
                         double saldoInicial,
                         double tasaInteres){

        super(numeroCuenta,titular,saldoInicial);

        this.tasaInteres = tasaInteres;

    }


    @Override
    public void retirar(double monto){

        if(monto <= 0){
            throw new IllegalArgumentException("Monto inválido");
        }


        if(getSaldo() >= monto){

            actualizarSaldo(-monto);

        }else{

            throw new IllegalStateException(
                    "Cuenta de ahorros no permite sobregiro"
            );

        }

    }


    @Override
    public void aplicarComisionMensual(){

        actualizarSaldo(-10);

    }

}
