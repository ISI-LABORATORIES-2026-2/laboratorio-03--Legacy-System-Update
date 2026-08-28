public class CuentaCorriente extends CuentaBancaria {


    private double cupoSobregiro;
    private double interesMoraDiario;


    public CuentaCorriente(String numeroCuenta,
                           String titular,
                           double saldoInicial,
                           double cupoSobregiro){

        super(numeroCuenta,titular,saldoInicial);

        this.cupoSobregiro = cupoSobregiro;
        this.interesMoraDiario = 0.02;

    }


    @Override
    public void retirar(double monto){


        if(monto <= 0){

            throw new IllegalArgumentException(
                    "Monto inválido"
            );

        }


        if(getSaldo() + cupoSobregiro >= monto){

            actualizarSaldo(-monto);


        }else{

            throw new IllegalStateException(
                    "Supera el cupo autorizado"
            );

        }

    }



    public double calcularInteresSobregiro(){


        if(saldo < 0){

            return Math.abs(saldo) * interesMoraDiario;

        }


        return 0;

    }



    @Override
    public void aplicarComisionMensual(){


        actualizarSaldo(-20);


    }

}
