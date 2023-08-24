import java.util.Random;

public class Luta {
    Character p1, p2;
    int turno;
    boolean p1IsAttacking;
    String winner;

    Random r;

    public int roll(int min, int max) {
        return r.nextInt(max - min) + min;
    }

    public Luta(Character p1, Character p2) {
        this.p1 = p1;
        this.p2 = p2;
        turno = 0;
        winner = "";
        r = new Random();
        rolaIniciativa();
    }

    public boolean isEveryoneAlive() {
        return (p1.getVida() > 0 && p2.getVida() > 0);
    }

    public boolean hasP1Won() {
        return p2.getVida() < 1;
    }

    public boolean hasP2Won() {
        return p1.getVida() < 1;
    }

    public String proximoTurno() {
        String saida = "";
        if (isEveryoneAlive()) {
            turno++;
            saida += "Turno " + turno + ": ";
            int atk, def;
            if (p1IsAttacking) {
                if (p1.hasDesvantagemCodigoCombate && p2.hasPerda) {
                    saida += p1.getNome() + " não ataca devido ao sei Código de Combate.";
                    p2.hasPerda = false;
                } else {
                    // sorteia ataque e verifica se tem mana
                    AtaqueEspecial ataqueEscolhido;
                    int sorteado = roll(0, p1.ataques.size());
                    do {
                        ataqueEscolhido = p1.ataques.get(sorteado);
                        sorteado--;
                    } while (p1.getMana() < ataqueEscolhido.custoDeMana && ataqueEscolhido.custoDeMana != 0);
                    p1.setMana(p1.getMana() - ataqueEscolhido.custoDeMana);
                    // sorteia defesa e verifica se tem mana
                    DefesaEspecial defesaEscolhida;
                    sorteado = roll(0, p2.defesas.size());
                    do {
                        defesaEscolhida = p2.defesas.get(sorteado);
                        sorteado--;
                    } while (p2.getMana() < defesaEscolhida.custoDeMana && defesaEscolhida.custoDeMana != 0);
                    p2.setMana(p2.getMana() - defesaEscolhida.custoDeMana);

                    if (ataqueEscolhido.isPenetrante()) p2.hasPerda = true;
                    atk = p1.ataque(ataqueEscolhido);
                    def = p2.defesa(defesaEscolhida);
                    if (def < atk * 2) {
                        if (def >= atk) {
                            if (defesaEscolhida.isReflexao()) {
                                saida += p2.getNome() + " devolve " + (def - atk) + " de dano.";
                                p1.setVida(p1.getVida() - (def - atk));
                            }
                            if (ataqueEscolhido.isEspiritual()) {
                                p2.setMana(p2.getMana() - 1);
                                saida += p2.getNome() + " perde 1 de mana.";
                            } else {
                                p2.setVida(p2.getVida() - 1);
                                saida += p2.getNome() + " leva 1 de dano.";
                            }
                        } else {
                            if (ataqueEscolhido.isEspiritual()) {
                                p2.setMana(p2.getMana() - (atk - def));
                                saida += p2.getNome() + " perde " + (atk - def) + " de mana.";
                            } else {
                                p2.setVida(p2.getVida() - (atk - def));
                                saida += p2.getNome() + " leva " + (atk - def) + " de dano.";
                            }
                        }
                    } else {
                        if (defesaEscolhida.isReflexao()) {
                            p1.setVida(p1.getVida() - (def - atk));
                            saida += p2.getNome() + " bloqueia totalmente o golpe e devolve " + (def - atk) + " de dano.";
                        } else {
                            saida += p2.getNome() + " bloqueia totalmente o golpe.";
                        }
                    }
                }
            } else {
                if (p2.hasDesvantagemCodigoCombate && p1.hasPerda) {
                    saida += p2.getNome() + " não ataca devido ao sei Código de Combate.";
                    p1.hasPerda = false;
                }
                // sorteia ataque e verifica se tem mana
                AtaqueEspecial ataqueEscolhido;
                int sorteado = roll(0, p2.ataques.size());
                do {
                    ataqueEscolhido = p2.ataques.get(sorteado);
                    sorteado--;
                } while (p2.getMana() < ataqueEscolhido.custoDeMana && ataqueEscolhido.custoDeMana != 0);
                p2.setMana(p2.getMana() - ataqueEscolhido.custoDeMana);
                // sorteia defesa e verifica se tem mana
                DefesaEspecial defesaEscolhida;
                sorteado = roll(0, p1.defesas.size());
                do {
                    defesaEscolhida = p1.defesas.get(sorteado);
                    sorteado--;
                } while (p1.getMana() < defesaEscolhida.custoDeMana && defesaEscolhida.custoDeMana != 0);
                p1.setMana(p1.getMana() - defesaEscolhida.custoDeMana);

                if (ataqueEscolhido.isPenetrante()) p1.hasPerda = true;
                atk = p2.ataque(ataqueEscolhido);
                def = p1.defesa(defesaEscolhida);
                if (def < atk * 2) {
                    if (def >= atk) {
                        if (defesaEscolhida.isReflexao()) {
                            saida += p1.getNome() + " devolve " + (def - atk) + " de dano.";
                            p2.setVida(p2.getVida() - (def - atk));
                        }
                        if (ataqueEscolhido.isEspiritual()) {
                            p1.setMana(p1.getMana() - 1);
                            saida += p1.getNome() + " perde 1 de mana.";
                        } else {
                            p1.setVida(p1.getVida() - 1);
                            saida += p1.getNome() + " leva 1 de dano.";
                        }
                    } else {
                        if (ataqueEscolhido.isEspiritual()) {
                            p1.setMana(p1.getMana() - (atk - def));
                            saida += p1.getNome() + " perde " + (atk - def) + " de mana.";
                        } else {
                            p1.setVida(p1.getVida() - (atk - def));
                            saida += p1.getNome() + " leva " + (atk - def) + " de dano.";
                        }
                    }
                } else {
                    if (defesaEscolhida.isReflexao()) {
                        p2.setVida(p2.getVida() - (def - atk));
                        saida += p1.getNome() + " bloqueia totalmente o golpe e devolve " + (def - atk) + " de dano.";
                    } else {
                        saida += p1.getNome() + " bloqueia totalmente o golpe.";
                    }
                }

            }
            if (p2.getVida() < 1) {
                winner = p1.getNome();
                saida += "\n****************\n" + p1.getNome() + " é o vencedor!";
            }else if (p1.getVida() < 1) {
                winner = p2.getNome();
                saida += "\n****************\n" + p2.getNome() + " é o vencedor!";
            }
            p1IsAttacking = !p1IsAttacking;
        }
        return saida;
    }

    public void proximaRodada() {

        proximoTurno();
        proximoTurno();
    }

    private void rolaIniciativa() {
        int initp1 = p1.iniciativa();
        int initp2 = p2.iniciativa();
        if (initp2 == initp1) {
            if (p1.hasVantagemAgil && !p2.hasVantagemAgil) {
                p1IsAttacking = true;
            } else if (p2.hasVantagemAgil && !p1.hasVantagemAgil) {
                p1IsAttacking = false;
            } else {
                if (p1.getHabilidade() > p2.getHabilidade()) {
                    p1IsAttacking = true;
                } else if (p2.getHabilidade() > p1.getHabilidade()) {
                    p1IsAttacking = false;
                } else {
                    // rollies
                    p1IsAttacking = roll(1, 3) == 1;
                }
            }
        } else if (initp1 > initp2) {
            p1IsAttacking = true;
        } else {
            p1IsAttacking = false;
        }
    }

    public void reset() {
        p1.reset();
        p2.reset();
        turno=0;
        rolaIniciativa();
        winner="";
    }
}
