public class Main {
    public static void main(String[] args) {

        Character rin = new Character("Rin Asakusa", 2, 3, 2);
        rin.hasPericiaLuta = true;
        rin.hasVantagemMaestriaLuta = true;
        rin.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.POTENTE}));
        rin.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.TENAZ}));
        rin.hasDesvantagemCodigoCombate = true;

        Character gatimu = new Character("Gatimu Ngugi", 3, 2, 2);
        gatimu.hasPericiaLuta = true;
        gatimu.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.PERIGOSO}));
        gatimu.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.POTENTE}));
        gatimu.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.TENAZ}));
        gatimu.hasDesvantagemCodigoCombate = true;

        Character dani = new Character("Dani da Silva", 2, 2, 3);
        dani.hasPericiaLuta = true;
        dani.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.PODEROSO}));
        dani.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.POTENTE}));
        dani.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.REFLEXAO}));
        dani.hasDesvantagemCodigoCombate = true;

        Character chelsea = new Character("Chelsea Smith", 1, 3, 2);
        chelsea.hasPericiaLuta = true;
        chelsea.hasVantagemAgil = true;
        chelsea.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.PRECISO}));
        chelsea.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.ESQUIVA}));
        chelsea.hasDesvantagemCodigoCombate = true;

        Character kel = new Character("Kel", 3, 2, 3);
        kel.hasPericiaLuta = true;
        kel.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.PENETRANTE}));

        Character karator = new Character("Karator", 3, 1, 4);
        karator.hasPericiaLuta = true;
        karator.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.PODEROSO}));

        Character tiana = new Character("Tiana", 1, 4, 1);
        tiana.hasPericiaLuta = true;
        tiana.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.POTENTE}));
        tiana.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.ESQUIVA, DefesaEspecial.TipoDefesaEspecial.REFLEXAO}));

        Character shin = new Character("Shin", 2, 3, 2);
        shin.hasPericiaLuta = true;
        shin.hasVantagemAgil = true;
        shin.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.PERIGOSO}));
        shin.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.TENAZ}));

        Character general = new Character("General Púrpura", 4, 3, 4);
        general.hasPericiaLuta = true;
        general.ataques.add(new AtaqueEspecial(new AtaqueEspecial.TipoAtaqueEspecial[]{AtaqueEspecial.TipoAtaqueEspecial.POTENTE, AtaqueEspecial.TipoAtaqueEspecial.PERIGOSO}));
        general.defesas.add(new DefesaEspecial(new DefesaEspecial.TipoDefesaEspecial[]{DefesaEspecial.TipoDefesaEspecial.ROBUSTA}));

        Character p1, p2;
        p1 = new Character("P1", 3, 2, 2);
        p2 = new Character("P2", 2, 2, 3);

        Character[] roster = new Character[]{p1, p2, rin, gatimu, dani, chelsea, kel, karator, tiana, shin, general};

        int p1Wins = 0, p2Wins = 0, empates=0;
//        int quantidade = Integer.MAX_VALUE;
        int quantidade = 1000000;
        Luta l = new Luta(p1,p2);

        int j = 0;
        for (int i = 0; i < quantidade; i++) {

            while (l.winner.equals("")) {
//                System.out.println(l.proximoTurno());
                l.proximaRodada();
                if (l.hasP2Won() && l.hasP1Won())
                    empates++;
                else if (l.hasP1Won())
                    p1Wins++;
                else if (l.hasP2Won()) p2Wins++;
            }
            l.reset();

            if (j == 1000000) {
                System.out.println((double) i / quantidade * 100 + "%");
                j = -1;
            }
            j++;
        }

        System.out.println("Empates: " + empates + "(" + ((double) empates / quantidade * 100) + "%)");
        System.out.println("Vitórias p1: " + p1Wins + "(" + ((double) p1Wins / quantidade * 100) + "%)");
        System.out.println("Vitórias p2: " + p2Wins + "(" + ((double) p2Wins / quantidade * 100) + "%)");
    }
}
