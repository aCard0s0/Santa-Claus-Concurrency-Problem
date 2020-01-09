package structures.Mem;

/**
 *    Descrição geral:
 *       este tipo de dados define uma memória de tipo fifo derivada a partir
 *       de uma memória genérica que foi construída de uma forma paramétrica.
 * @param <R>
 */

public class MemFIFO<R> extends MemObject<R>
{
  /**
   *  Definição da memória de tipo fifo
   */

   private int inPnt, outPnt; // ponteiros de inserção e de retirada de um valor
   private boolean empty;     // sinalização de memória vazia
   private int size;

  /**
   *    Construtor de variáveis
   *    @param storage
   *    @throws MemException
   */
    public MemFIFO (R [] storage) throws MemException
    {
        super (storage);
        inPnt = outPnt = size = 0;
        empty = true;
    }

  /**
   *    fifo in -- escrita de um valor
   *    @param val
   *    @throws MemException
   */
    @Override
    public void write (R val) throws MemException
    {
        if ((inPnt != outPnt) || empty){ 
            mem[inPnt] = val;
            inPnt = (inPnt + 1) % mem.length;
            empty = false;
            size += 1;
        }
        else 
            throw new MemException ("Fifo full!");
    }

  /**
   *    fifo out -- leitura de um valor
   *    @return 
   *    @throws MemException
   */
    @Override
    public R read () throws MemException
    {
        R val;
        if (!empty){ 
            val = mem[outPnt];
            outPnt = (outPnt + 1) % mem.length;
            empty = (inPnt == outPnt);
            size -= 1;
        } else 
            throw new MemException ("Fifo empty!");
        return val;
    }
    
    public int size(){
        if (empty || size < 0)
            return 0;
        else
            return size;
    }
    
    public boolean isEmpty(){
        return empty;
    }
}
