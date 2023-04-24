import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Magasin {

    public static abstract class Article {
        protected String nom;
        protected String description;

        public Article(String nom, String description) {
            this.nom = nom;
            this.description = description;
        }

        public abstract double getPrix();
    }

    public static class ArticleUnite extends Article {
        private double prixUnite;

        public ArticleUnite(String nom, String description, double prixUnite) {
            super(nom, description);
            this.prixUnite = prixUnite;
        }

        public double getPrix() {
            return prixUnite;
        }
    }

    public static class ArticlePoids extends Article {
        private double prixKilo;

        public ArticlePoids(String nom, String description, double prixKilo) {
            super(nom, description);
            this.prixKilo = prixKilo;
        }

        public double getPrix() {
            return prixKilo;
        }
    }

    public static class AppareilElectronique extends Article {
        private Date dateAchat;

        public AppareilElectronique(String nom, String description, Date dateAchat) {
            super(nom, description);
            this.dateAchat = dateAchat;
        }

        public double getPrix() {
            return 0;
        }
    }

    public static class Panier {
        private List<Article> articles = new ArrayList<>();

        public void ajouterArticle(Article article) {
            articles.add(article);
        }

        public void afficherContenu() {
            for (Article article : articles) {
                System.out.println("- " + article.nom + " (" + article.description + ")");
            }
        }

        public double getPrixTotal() {
            double total = 0;
            for (Article article : articles) {
                total += article.getPrix();
            }
            return total;
        }
    }

    public static Article creerArticle() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez choisir le type d'article à créer:");
        System.out.println("1 - Article vendu à l'unité");
        System.out.println("2 - Article vendu au poids");
        System.out.println("3 - Appareil électronique");
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne restante

        System.out.println("Veuillez entrer le nom de l'article:");
        String nom = scanner.nextLine();

        System.out.println("Veuillez entrer la description de l'article:");
        String description = scanner.nextLine();

        Article nouvelArticle = null;
        switch (choix) {
            case 1:
                System.out.println("Veuillez entrer le prix à l'unité:");
                double prixUnite = scanner.nextDouble();
                nouvelArticle = new ArticleUnite(nom, description, prixUnite);
                break;
            case 2:
                System.out.println("Veuillez entrer le prix au kilo:");
                double prixKilo = scanner.nextDouble();
                nouvelArticle = new ArticlePoids(nom, description, prixKilo);
                break;
            case 3:
                System.out.println("Veuillez entrer la date d'achat de l'appareil électronique (format: yyyy-mm-dd):");
                String dateAchat = scanner.next();
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateAchat);
                    nouvelArticle = new AppareilElectronique(nom, description, date);
                } catch (ParseException e) {
                    System.out.println("Erreur: Date au format incorrect.");
                }
                break;
            default:
                System.out.println("Erreur: Choix invalide.");
        }

        return nouvelArticle;
    }

    public static void main(String[] args) {
        List<Article> articles = new ArrayList<>(Arrays.asList(
                new ArticleUnite("Livre", "Un livre intéressant", 15),
                new ArticlePoids("Pomme", "Pomme verte délicieuse", 2),
                new AppareilElectronique("Smartphone", "Un smartphone puissant", new Date())
        ));

        Panier panier = new Panier();

        Scanner scanner = new Scanner(System.in);
        int choix;
        boolean continuer = true;

        while (continuer) {
            System.out.println("\nMenu:");
            System.out.println("1 - Créer un nouvel article");
            System.out.println("2 - Voir la liste des produits");
            System.out.println("3 - Ajouter un produit au panier");
            System.out.println("4 - Voir le contenu du panier et le prix total");
            System.out.println("5 - Quitter");
            System.out.print("Veuillez choisir une option: ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    Article nouvelArticle = creerArticle();
                    if (nouvelArticle != null) {
                        articles.add(nouvelArticle);
                    }
                    break;
                case 2:
                    System.out.println("\nListe des produits à la vente:");
                    for (Article article : articles) {
                        System.out.println("- " + article.nom + " (" + article.description + ")");
                    }
                    break;
                case 3:
                    System.out.println("Veuillez entrer le nom de l'article à ajouter au panier:");
                    scanner.nextLine(); // Consommer la ligne restante
                    String nomArticle = scanner.nextLine();
                    boolean articleTrouve = false;
                    for (Article article : articles) {
                        if (article.nom.equalsIgnoreCase(nomArticle)) {
                            panier.ajouterArticle(article);
                            System.out.println("Article ajouté au panier.");
                            articleTrouve = true;
                            break;
                        }
                    }
                    if (!articleTrouve) {
                        System.out.println("Erreur: Aucun article trouvé avec ce nom.");
                    }
                    break;
                case 4:
                    System.out.println("\nContenu du panier:");
                    panier.afficherContenu();
                    System.out.println("\nPrix total du panier: " + panier.getPrixTotal() + "€");
                    break;
                case 5:
                    continuer = false;
                    break;
                default:
                    System.out.println("Erreur: Choix invalide.");
            }
        }
    }
}