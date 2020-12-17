import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoServiceIF extends Remote {

    double saldo(String conta) throws RemoteException;
    int quantidadeContas() throws RemoteException;
    String cadastrarConta(String numero, double saldo) throws RemoteException;
    Conta pesquisarConta(String numero) throws RemoteException;
    String removerConta(String numero) throws RemoteException;
}
