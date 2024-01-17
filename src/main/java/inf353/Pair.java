package inf353;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable {

    private T1 key;
    private T2 value;

    /**
     * constructeur d'un tuple de <key,value>
     * 
     * @param key   : la valeur du premier champ
     * @param value : la valeur du deuxième champ
     * @author squadSkat
     */
    public Pair(T1 key, T2 value) {

        this.key = key;
        this.value = value;
    }

    /**
     * setter of key
     * 
     * @param key : la valeur du premier champ
     * @author squadSkat
     */
    public void setKey(T1 key) {

        this.key = key;
    }

    /**
     * setter of value
     * 
     * @param value : la valeur du deuxième champ
     * @author squadSkat
     */
    public void setValue(T2 value) {

        this.value = value;
    }

    /**
     * getter of key
     * 
     * @author squadSkat
     */
    public T1 getKey() {

        return this.key;
    }

    /**
     * getter of value
     * 
     * @author squadSkat
     */
    public T2 getValue() {

        return this.value;
    }
}
