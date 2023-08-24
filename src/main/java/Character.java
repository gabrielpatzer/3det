import java.util.ArrayList;
import java.util.Random;

public class Character {
    private int poder, habilidade, resistencia;
    private String nome;
    private int acao, mana, vida;
    public boolean hasPerda, hasGanho;
    public boolean hasPericiaLuta;
    public boolean hasVantagemAgil, hasVantagemMaestriaLuta, hasVantagemAtaqueEspecial, hasVantagemDefesaEspecial;
    ArrayList<AtaqueEspecial> ataques;
    ArrayList<DefesaEspecial> defesas;
    public boolean hasDesvantagemAtrapalhado, hasDesvantagemCodigoCombate;

    private final Random r;

    public Character(String nome, int poder, int habilidade, int resistencia) {
        this.nome = nome;
        this.poder = poder;
        this.habilidade = habilidade;
        this.resistencia = resistencia;
        this.acao = poder;
        this.mana = habilidade * 5;
        this.vida = resistencia * 5;
        this.hasGanho = false;
        this.hasPerda = false;
        this.hasPericiaLuta = false;
        this.hasVantagemAgil = false;
        this.hasVantagemMaestriaLuta = false;
        hasVantagemAtaqueEspecial = false;
        hasVantagemDefesaEspecial = false;
        hasDesvantagemAtrapalhado = false;
        hasDesvantagemCodigoCombate = false;

        ataques = new ArrayList<>();
        ataques.add(new AtaqueEspecial());
        defesas = new ArrayList<>();
        defesas.add(new DefesaEspecial());

        this.r = new Random();
    }

    public void reset() {
        this.acao = poder;
        this.mana = habilidade * 5;
        this.vida = resistencia * 5;
        this.hasGanho = false;
        this.hasPerda = false;
    }

    public int roll(int min, int max) {
        return r.nextInt(max - min) + min;
    }

    public int iniciativa() {
        int resultado = 0;
        int dados = 1;
        if (hasGanho) {
            dados++;
            hasGanho = false;
        }
        if (hasPerda) {
            dados--;
            hasPerda = false;
        }
        if (hasDesvantagemAtrapalhado) dados--;
        if (dados > 3) dados = 3;
        else if (dados < 1) dados = 1;
        else if (acao > 0 && dados < 3 && roll(1, 100) < 50) {
            acao--;
            dados++;
        }
        boolean hasCritsOn5Or6 = false;

        if (hasVantagemAgil) {
            resultado += 2;
            // chance for 2mana/5crit
            if (mana > 2 && roll(1, 100) < 100) {
                mana -= 2;
                hasCritsOn5Or6 = true;
            }
        }
        boolean couldCritFumble = true;
        for (int n = 0; n < dados; n++) {
            int temp = roll(1, 7);
            if (temp == 6) {
                if (hasDesvantagemAtrapalhado) resultado += temp;
                else resultado += temp + habilidade;
                couldCritFumble = false;
            } else if (temp > 1) {
                couldCritFumble = false;
                resultado += temp;
            } else {
                resultado += temp;
                if (hasCritsOn5Or6 && temp == 5) resultado += habilidade;
            }
        }
        if (couldCritFumble) hasPerda = true;
        return resultado;
    }

    public int ataque(AtaqueEspecial ataqueEscolhido) {
        int resultado = 0;
        boolean hasCritsOn5Or6 = false;


        if (ataqueEscolhido.isPerigoso()) hasCritsOn5Or6 = true;
        else if (hasVantagemMaestriaLuta && mana > 0 && roll(1, 100) < 100) {
            mana -= 1;
            hasCritsOn5Or6 = true;
        }


        int dados = 1;
        if (hasGanho) {
            dados++;
            hasGanho = false;
        }
        if (hasPerda) {
            dados--;
            hasPerda = false;
        }
        if (hasPericiaLuta) dados++;
        if (dados > 3) dados = 3;
        else if (dados < 1) dados = 1;
        else if (acao > 0 && dados < 3 && roll(1, 100) < 100) {
            acao--;
            dados++;
        }
        boolean couldCritFumble = true;
        for (int n = 0; n < dados; n++) {
            int temp = roll(1, 7);
            if (temp == 6) {
                if (ataqueEscolhido.isPreciso())
                    resultado += temp + habilidade;
                else
                    resultado += temp + poder;
                if (ataqueEscolhido.isPoderoso()) {
                    if (ataqueEscolhido.isPreciso())
                        resultado += habilidade;
                    else
                        resultado += poder;
                }
                couldCritFumble = false;
            } else if (temp > 1) {
                resultado += temp;
                couldCritFumble = false;
                if (hasCritsOn5Or6 && temp == 5)
                    if (ataqueEscolhido.isPreciso()) resultado += habilidade;
                    else resultado += poder;
            } else {
                resultado += temp;
            }
        }
        if (ataqueEscolhido.isPotente() > 0) resultado += ataqueEscolhido.isPotente() * 2;
        if (ataqueEscolhido.isPreciso())
            resultado += habilidade;
        else
            resultado += poder;
        if (couldCritFumble) hasPerda = true;
        return resultado;
    }

    public int defesa(DefesaEspecial defesaEscolhida) {
        int resultado = 0;
        boolean hasCritsOn5Or6 = false;
        if (hasVantagemMaestriaLuta && mana > 0 && roll(1, 100) < 50) {
            mana -= 1;
            hasCritsOn5Or6 = true;
        }

        int dados = 1;
        if (hasGanho) {
            dados++;
            hasGanho = false;
        }
        if (hasPerda) {
            dados--;
            hasPerda = false;
        }
        if (hasPericiaLuta) dados++;
        if (dados > 3) dados = 3;
        else if (dados < 1) dados = 1;
        else if (acao > 0 && dados < 3 && roll(1, 100) < 100) {
            acao--;
            dados++;
        }

        boolean couldCritFumble = true;
        for (int n = 0; n < dados; n++) {
            int temp = roll(1, 7);
            if (temp == 6) {
                if (defesaEscolhida.isRobusta() && mana > 1 && roll(1, 100) < 100)
                    if (defesaEscolhida.isEsquiva()) resultado += habilidade;
                    else resultado += resistencia;
                if (defesaEscolhida.isEsquiva()) resultado += temp + habilidade;
                else resultado += temp + resistencia;
            } else if (temp > 1) {
                resultado += temp;
                couldCritFumble = false;
                if (hasCritsOn5Or6 && temp == 5)
                    if (defesaEscolhida.isEsquiva()) resultado += habilidade;
                    else resultado += resistencia;
            } else {
                resultado += temp;
            }
        }
        if (defesaEscolhida.isEsquiva()) resultado += habilidade;
        else resultado += resistencia;
        if (defesaEscolhida.isTenaz() > 0) resultado += defesaEscolhida.isTenaz() * 2;
        if (couldCritFumble) hasPerda = true;

        return resultado;
    }

    @Override
    public String toString() {
        return "Character{" +
                "poder=" + poder +
                ", habilidade=" + habilidade +
                ", resistencia=" + resistencia +
                ", nome='" + nome + '\'' +
                ", acao=" + acao +
                ", mana=" + mana +
                ", vida=" + vida +
                ", hasPericiaLuta=" + hasPericiaLuta +
                ", hasVantagemAgil=" + hasVantagemAgil +
                ", hasVantagemMaestriaLuta=" + hasVantagemMaestriaLuta +
                ", hasVantagemAtaqueEspecial=" + hasVantagemAtaqueEspecial +
                ", hasVantagemDefesaEspecial=" + hasVantagemDefesaEspecial +
                ", ataques=" + ataques +
                ", defesas=" + defesas +
                ", hasDesvantagemAtrapalhado=" + hasDesvantagemAtrapalhado +
                ", hasDesvantagemCodigoCombate=" + hasDesvantagemCodigoCombate +
                '}';
    }

    public int getAcao() {
        return acao;
    }

    public void setAcao(int acao) {
        this.acao = acao;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(int habilidade) {
        this.habilidade = habilidade;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
