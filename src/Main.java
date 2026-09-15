import Network.ConnectHost;
import Network.HostCreate;

void main() throws Exception {

    HostCreate host = new HostCreate(
            "1234",
            "testename",
            1,
            5000
    );

    ConnectHost connection = new ConnectHost();

    Thread hostThread = new Thread(() -> {
        try {
            host.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    hostThread.start();

    while (!host.isReady()) {
        Thread.sleep(10);
    }

    connection.connect(
            "127.0.0.1",
            5000,
            "pilha"
    );

    System.out.println(
            host.getRoom().getPlayers()
    );
}