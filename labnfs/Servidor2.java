package labnfs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Servidor2 {
    public static void main(String[] args) throws IOException {
        System.out.println("== Servidor ==");
        // Configurando o socket
        ServerSocket serverSocket = new ServerSocket(7001);
        Socket socket = serverSocket.accept();

        // pegando uma referência do canal de saída do socket. Ao escrever nesse canal, está se enviando dados para o
        // servidor
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        // pegando uma referência do canal de entrada do socket. Ao ler deste canal, está se recebendo os dados
        // enviados pelo servidor
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        Path origem = Paths.get("Z:\\carlos\\nfs-sockets");
        // laço infinito do servidor
        while (true) {
            System.out.println("Cliente: " + socket.getInetAddress());
            File dir = new File("Z:\\carlos\\nfs-sockets");
            String mensagem = dis.readUTF();
            System.out.println(mensagem);

//            dos.writeUTF("Li sua mensagem: " + mensagem);
//            System.out.println(origem.toAbsolutePath().getFileSystem().getFileStores().toString());
//            System.out.println(File.listRoots());
//            Files.walk(Paths.get("Z:\\carlos\\nfs-sockets"))
//                    .filter(Files::isRegularFile)
//                    .forEach(System.out::println);

            switch (mensagem) {
                case "readdir":
                    File folder = new File("Z:\\carlos\\nfs-sockets");
                    File[] listOfFiles = folder.listFiles();
                    String out = "\n";
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            out += file.getName() + "\n";
                            System.out.println(file.getName());
                        }
                    }
                    dos.writeUTF(out);
                    break;
                case "create":
                    File newfile = new File("Z:\\carlos\\nfs-sockets\\file.txt");
                    newfile.createNewFile();
                    break;
                case "delete":
                    File delfile = new File("Z:\\carlos\\nfs-sockets\\file.txt");
                    delfile.delete();
                    break;
            }



        }


        /*
         * Observe o while acima. Perceba que primeiro se lê a mensagem vinda do cliente (linha 29, depois se escreve
         * (linha 32) no canal de saída do socket. Isso ocorre da forma inversa do que ocorre no while do Cliente2,
         * pois, de outra forma, daria deadlock (se ambos quiserem ler da entrada ao mesmo tempo, por exemplo,
         * ninguém evoluiria, já que todos estariam aguardando.
         */
    }


}
