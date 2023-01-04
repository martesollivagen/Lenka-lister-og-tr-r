import java.util.Objects;

/**
 * gjort av Anna Marie Bøe Tvedt og Marte Solli Vågen
 */
public class ListeOppgave {

    public static void main(String[] args) {
        System.out.println(RegnUt.regnUt("100 + 9999999999999999999999") + "\n\n");

        Tre tre = new Tre();
        tre.settInnSetning("hode bein arm tann hånd tåre ballong aar saft håi tå tååegl");

        System.out.println(tre.visTre());
    }

    //Deloppgave 1, lenka lister
    public static class RegnUt{

        private static LangeTall lagNyttTall(String tall){
            String[] liste = tall.split("");
            LangeTall nyTall = new LangeTall();

            for (String i: liste) {
                nyTall.leggTilNyNode(Integer.parseInt(i));
            }
            return nyTall;
        }

        private static LangeTall finnStørsteTall(LangeTall en, LangeTall to){
            if (en.getLengde() == to.getLengde()){
                Node temp1 = en.getHale();
                Node temp2 = to.getHale();
                while (temp1.finnElement() == temp2.finnElement()){
                    temp1 = temp1.finnForrige();
                    temp2 = temp2.finnForrige();
                }
                return (temp1.finnElement()>temp2.finnElement()) ? en : to;
            }
            else if (en.getLengde()>to.getLengde()) {
                return en;
            }
            else return to;
        }

        public static String addere(String tall1, String tall2){
            LangeTall en = lagNyttTall(tall1);
            LangeTall to = lagNyttTall(tall2);
            LangeTall resultat = new LangeTall();

            int min = Math.min(en.getLengde(), to.getLengde());
            int max = finnStørsteTall(en,to).getLengde();

            Node temp1 = en.getHode();
            Node temp2 = to.getHode();
            int element = 0;

            for (int i = 0; i<min; i++){
                element += temp1.finnElement() + temp2.finnElement();
                resultat.leggTilNyNode((element)%10);

                if (element >= 10){
                    element=1;
                }else {
                    element = 0;
                }

                temp1 = temp1.finnNeste();
                temp2 = temp2.finnNeste();
            }

            for (int i = 0; i<max-min; i++){
                if (en == finnStørsteTall(en,to)){
                    element += temp1.finnElement();
                    resultat.leggTilNyNode((element)%10);
                    temp1 = temp1.finnNeste();
                } else {
                    element += temp2.finnElement();
                    resultat.leggTilNyNode((element)%10);
                    temp2 = temp2.finnNeste();
                }

                if (element >= 10){
                    element=1;
                }else {
                    element = 0;
                }
            }

            if (element == 1){
                resultat.leggTilNyNode(1);
            }

            return resultat.visListe();
        }

        public static String subtrahere(String tall1, String tall2){
            if (Objects.equals(tall1, tall2)){return "0";}

            LangeTall en = lagNyttTall(tall1);
            LangeTall to = lagNyttTall(tall2);
            LangeTall resultat = new LangeTall();

            int min = Math.min(en.getLengde(), to.getLengde());
            int max = finnStørsteTall(en,to).getLengde();

            Node temp1 = en.getHode();
            Node temp2 = to.getHode();
            int element = 0;

            for (int i = 0; i<min; i++){
                if (en == finnStørsteTall(en, to)){
                    element += temp1.finnElement() - temp2.finnElement();
                } else {
                    element += temp2.finnElement() - temp1.finnElement();
                }

                if (element<0){
                    resultat.leggTilNyNode((element)+10);
                    element=-1;
                }else{
                    resultat.leggTilNyNode((element));
                    element = 0;
                }

                temp1 = temp1.finnNeste();
                temp2 = temp2.finnNeste();
            }


            for (int i = 0; i<max-min; i++){
                if (en == finnStørsteTall(en,to)){
                    element += temp1.finnElement();
                    temp1 = temp1.finnNeste();
                } else {
                    element += temp2.finnElement();
                    temp2 = temp2.finnNeste();
                }

                if (element<0){
                    resultat.leggTilNyNode((element)+10);
                    element=-1;
                }else {
                    resultat.leggTilNyNode((element));
                    element = 0;
                }
            }

            if (to == finnStørsteTall(en, to)){
                return "-" + resultat.visListe();
            }

            return resultat.visListe();
        }


        public static String regnUt(String regneStykke){
            String[] valg = regneStykke.split(" ");
            if (valg.length==3){
                if (Objects.equals(valg[1], "+")){
                    return "  " + valg[0] + " + " + valg[2] + "\n= " +
                            addere(valg[0], valg[2]);
                } else if(Objects.equals(valg[1], "-")){
                    return "  " + valg[0] + " - " + valg[2] + "\n= " +
                            subtrahere(valg[0], valg[2]);
                }
            }
            return "Feil input";
        }
    }

    public static class LangeTall {

        private Node hode;
        private Node hale;
        private int lengde;

        public LangeTall() {
            this.hode = null;
            this.hale = null;
            this.lengde = 0;
        }

        public boolean erTom() {
            return lengde == 0;
        }

        public int getLengde() {
            return lengde;
        }

        public Node getHode(){
            return hode;
        }

        public Node getHale(){
            return hale;
        }

        public String visListe() {
            StringBuilder langTall = new StringBuilder();

            if (hode == null) {
                return null;
            }
            Node temp = hode;
            while (temp != null) {
                langTall.append(((temp.element)));
                temp = temp.neste;
            }

            return langTall.toString();
        }

        public void leggTilNyNode(int verdi) {
            Node nyNode = new Node(verdi);
            if (erTom()) {
                hale = nyNode;
            } else {
                hode.forrige = nyNode;
            }
            nyNode.neste = hode;
            hode = nyNode;
            lengde++;
        }
    }

    private static class Node{
        private final int element;
        private Node neste;
        private Node forrige;

        public Node(int element){
            this.element = element;
            this.neste = null;
            this.forrige = null;
        }

        public int finnElement() {
            return element;
        }

        public Node finnNeste() {
            return neste;
        }

        public Node finnForrige() {
            return forrige;
        }
    }


    //Deloppgave 2, trær
    public static class TreNode {
        private String element ;
        private TreNode venstre;
        private TreNode hoyre;
        private TreNode forelder;


        public TreNode(String e, TreNode v, TreNode h, TreNode f){
            this.element = e;
            this.venstre = v;
            this.hoyre = h;
            this.forelder = f;
        }

        public TreNode(String e){
            this.element = e;
        }

        public String getElement() {
            return element;
        }

        public TreNode getVenstre() {
            return venstre;
        }

        public TreNode getHoyre() {
            return hoyre;
        }
    }

    public static class Tre {
        private TreNode rot;

        public Tre(){
            rot = null;
        }

        public String visTre(){
            StringBuilder sb = new StringBuilder();
            int tall = 64;
            int i = 0;
            TreNode denne;

            Kø kø = new Kø(15);
            kø.leggIKØ(rot);

            while (!kø.tom() && tall > 4){
                for (int j = 0; j<(Math.pow(2,i)); j++){
                    if (kø.sjekkKø() != null){
                        denne = (TreNode) kø.nesteIKØ();
                        sb.append(" ".repeat((tall - denne.getElement().length()) / 2));
                        sb.append(denne.getElement());
                        sb.append(" ".repeat((tall - denne.getElement().length()) / 2));

                        kø.leggIKØ(denne.getVenstre());
                        kø.leggIKØ(denne.getHoyre());
                    }else{
                        sb.append(" ".repeat((tall) / 2^(i)));
                        kø.nesteIKØ();
                    }
                }
                tall /=2;
                sb.append("\n");
                i++;
            }

            return sb.toString();
        }

        public void settInnSetning(String setning){
            String[] liste = setning.split(" ");
            for (String ord: liste) {
                settInn(ord);
            }
        }

        public void settInn(String e){
            TreNode n = rot;
            if (rot == null){
                rot = new TreNode(e, null,null,null);
                return;
            }
            String sml = "";
            TreNode f = null;
            while (n != null){
                f = n;
                sml = n.getElement();
                if(sjekkAlfabet(e,sml)){
                    n = n.getVenstre();
                }else {
                    n = n.getHoyre();}
            }
            if (sjekkAlfabet(e, sml)){
                f.venstre = new TreNode(e, null, null, f);
            }else {
                f.hoyre =  new TreNode(e, null, null, f);
            }
        }

        public boolean sjekkAlfabet(String ord1, String ord2){
            return ord1.compareToIgnoreCase(ord2) < 0;
        }
    }

    public static class Kø {
        private Object[] tab;
        private int start = 0;
        private int slutt = 0;
        private int antall = 0;

        public Kø(int str){
            tab = new Object[str];
        }

        public boolean tom(){
            return antall == 0;
        }

        public boolean full(){
            return tab.length == antall;
        }


        public void leggIKØ(Object e){
            if (full()) return;
            tab[slutt] = e;
            slutt = (slutt + 1)% tab.length;
            antall++;
        }

        public Object nesteIKØ(){
            if(!tom()){
                Object e = tab[start];
                start = (start+1)%tab.length;
                antall--;
                return e;
            }
            else return null;
        }

        public Object sjekkKø(){
            if (!tom()) return tab[start];
            else return null;
        }
    }

}
