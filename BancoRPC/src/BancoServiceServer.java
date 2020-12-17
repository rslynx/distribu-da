import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class BancoServiceServer extends UnicastRemoteObject implements BancoServiceIF {

    private List<Conta> contas;

    public BancoServiceServer() throws RemoteException {
        contas = new ArrayList<Conta>();
        contas.add(new Conta("1", 100.0));
        contas.add(new Conta("2", 200.0));
    }

    @Override
    public double saldo(String numero) throws RemoteException {
        Conta c = pesquisarConta(numero);
        if(c == null)
            throw new RemoteException("Conta inexistente");

        return c.getSaldo();
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        return contas.size();
    }

    @Override
    public String cadastrarConta(String numero, double saldo) throws RemoteException {
        Conta c = pesquisarConta(numero);
        if(c == null) {
            contas.add(new Conta(numero, saldo));
            return "Conta " + numero + " adicionada com sucesso!";
        }
        else
            throw new RemoteException("Conta já adicionada");

    }

    @Override
    public Conta pesquisarConta(String numero) throws RemoteException {
        Conta c = null;
        for (Conta conta: contas) {
            if (conta.getNumero().equals(numero))
                c = conta;
        }
        return c;
    }

    @Override
    public String removerConta(String numero) throws RemoteException {
        Conta c = pesquisarConta(numero);
        if(c == null)
            throw new RemoteException("Conta inexistente");

        contas.remove(c);
        return "Conta " + c.getNumero() + " removida com sucesso";

    }

}
