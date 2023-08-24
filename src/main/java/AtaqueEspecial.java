import java.util.ArrayList;
import java.util.Arrays;

public class AtaqueEspecial {
    public enum TipoAtaqueEspecial {
        ESPIRITUAL, POTENTE, PENETRANTE, PERIGOSO, PRECISO, PODEROSO;

    }
    int custoDeMana;

    ArrayList<TipoAtaqueEspecial> ataqueEspecial;

    public AtaqueEspecial(ArrayList<TipoAtaqueEspecial> modificadores) {
        ataqueEspecial = modificadores;
        int temp = 0;
        if (isEspiritual()) temp++;
        if (isPenetrante()) temp+=3;
        if (isPerigoso()) temp++;
        if (isPreciso()) temp+=2;
        if (isPoderoso()) temp+=2;
        temp += isPotente();
        custoDeMana = temp;
    }

    public AtaqueEspecial(TipoAtaqueEspecial[] modificadores) {
        this(new ArrayList<>(Arrays.asList(modificadores)));
    }
    public AtaqueEspecial(){
        custoDeMana = 0;
        ataqueEspecial = new ArrayList<>();
    }

    /**
     * 1PM - dano na mana ao invés de vida
     * @return
     */
    public boolean isEspiritual() {
        boolean ret = false;
        for (TipoAtaqueEspecial t : ataqueEspecial) {
            if (t.name().equals("ESPIRITUAL")) {
                ret = true;
                break;
            }
        }
        return ret;
    }
    /**
     * 2PM - em caso de crit adiciona poder mais uma vez
     * @return
     */
    public boolean isPoderoso() {
        boolean ret = false;
        for (TipoAtaqueEspecial t : ataqueEspecial) {
            if (t.name().equals("PODEROSO")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 3PM - defensor tem Perda na defesa
     * @return
     */
    public boolean isPenetrante() {
        boolean ret = false;
        for (TipoAtaqueEspecial t : ataqueEspecial) {
            if (t.name().equals("PENETRANTE")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 1PM - crítico com 5 ou 6
     * @return
     */
    public boolean isPerigoso() {
        boolean ret = false;
        for (TipoAtaqueEspecial t : ataqueEspecial) {
            if (t.name().equals("PERIGOSO")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 2PM - usa habilidade no lugar de poder
     * @return
     */
    public boolean isPreciso() {
        boolean ret = false;
        for (TipoAtaqueEspecial t : ataqueEspecial) {
            if (t.name().equals("PRECISO")) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * 1PM - +2 dano, pode acumular
     * @return
     */
    public int isPotente() {
        int ret = 0;
        for (TipoAtaqueEspecial t : ataqueEspecial) {
            if (t.name().equals("POTENTE")) ret++;
        }
        return ret;
    }
}
