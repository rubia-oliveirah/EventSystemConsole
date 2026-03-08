import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        EventSystem sistema = new EventSystem();

        // Cadastro inicial de usuário
        System.out.println("=== Cadastro de Usuário ===");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Cidade: ");
        String cidade = sc.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(sc.nextLine());

        User usuario = new User(nome, email, cidade, idade);
        sistema.cadastrarUsuario(usuario);

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar Evento");
            System.out.println("2 - Listar Eventos");
            System.out.println("3 - Participar de Evento");
            System.out.println("4 - Cancelar Participação");
            System.out.println("5 - Meus Eventos");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1:
                    cadastrarEvento(sistema);
                    break;
                case 2:
                    listarEventos(sistema);
                    break;
                case 3:
                    participarEvento(sistema, usuario);
                    break;
                case 4:
                    cancelarParticipacao(sistema, usuario);
                    break;
                case 5:
                    listarMeusEventos(usuario);
                    break;
                case 0:
                    rodando = false;
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarEvento(EventSystem sistema) {
        System.out.println("\n=== Cadastro de Evento ===");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        System.out.println("Categorias: FESTA, SHOW, ESPORTE, TEATRO, OUTRO");
        System.out.print("Categoria: ");
        Category categoria = Category.valueOf(sc.nextLine().toUpperCase());

        System.out.print("Horário (yyyy-MM-dd HH:mm): ");
        LocalDateTime horario = LocalDateTime.parse(sc.nextLine(), formatter);

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        Event evento = new Event(nome, endereco, categoria, horario, descricao);
        sistema.cadastrarEvento(evento);
        System.out.println("Evento cadastrado com sucesso!");
    }

    private static void listarEventos(EventSystem sistema) {
        System.out.println("\n=== Eventos Disponíveis ===");
        List<Event> eventos = sistema.listarEventos();
        for (int i = 0; i < eventos.size(); i++) {
            Event e = eventos.get(i);
            String status = e.estaOcorendoAgora() ? "(Ocorre agora)" : "";
            System.out.println(i + " - " + e + " " + status);
        }
    }

    private static void participarEvento(EventSystem sistema, User usuario) {
        listarEventos(sistema);
        System.out.print("Escolha o índice do evento para participar: ");
        int idx = Integer.parseInt(sc.nextLine());
        if (idx >= 0 && idx < sistema.listarEventos().size()) {
            sistema.participarEvento(usuario, sistema.listarEventos().get(idx));
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void cancelarParticipacao(EventSystem sistema, User usuario) {
        List<Event> meusEventos = usuario.getEventosConfirmados();
        System.out.println("\n=== Meus Eventos ===");
        for (int i = 0; i < meusEventos.size(); i++) {
            System.out.println(i + " - " + meusEventos.get(i));
        }
        System.out.print("Escolha o índice do evento para cancelar: ");
        int idx = Integer.parseInt(sc.nextLine());
        if (idx >= 0 && idx < meusEventos.size()) {
            sistema.cancelarParticipacao(usuario, meusEventos.get(idx));
        } else {
            System.out.println("Índice inválido.");
        }
    }

    private static void listarMeusEventos(User usuario) {
        System.out.println("\n=== Meus Eventos Confirmados ===");
        for (Event e : usuario.getEventosConfirmados()) {
            System.out.println(e);
        }
    }

