public abstract class CuentaBancaria {

    private final String numeroCuenta;
    private String titular;
    protected double saldo;


    public CuentaBancaria(String numeroCuenta,
                          String titular,
                          double saldoInicial) {

        if(numeroCuenta == null || numeroCuenta.isEmpty()){
            throw new IllegalArgumentException("Número inválido");
        }

        if(titular == null || titular.isEmpty()){
            throw new IllegalArgumentException("Titular inválido");
        }

        if(saldoInicial < 0){
            throw new IllegalArgumentException("Saldo inicial inválido");
        }


        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;

    }


    public abstract void retirar(double monto);


    public abstract void aplicarComisionMensual();


    public double getSaldo(){

        return saldo;

    }


    public String getNumeroCuenta(){

        return numeroCuenta;

    }


    public String getTitular(){

        return titular;

    }


    protected void actualizarSaldo(double valor){

        saldo += valor;

    }

}
