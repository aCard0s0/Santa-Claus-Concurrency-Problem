package structures.Mem;

/**
 *    Descrição geral:
 *       este tipo de dados define uma memória genérica que 
 *       foi construída de uma forma paramétrica.
 * @param <R>
 */
public abstract class MemObject<R>
{
  /**
   *  Definição da memória genérica
   */
    protected R [] mem;                    // área de armazenamento

  /**
   *    Construtor de variáveis
   *    @param storage
   *    @throws MemException
   */
    protected MemObject (R [] storage) throws MemException
    {
        if (storage.length > 0)
           mem = storage;
        else 
            throw new MemException ("Illegal storage size!");
    }

  /**
   *    escrita de um valor -- método virtual
   *    @param val
   *    @throws MemException
   */
    protected abstract void write (R val) throws MemException;

  /**
   *    leitura de um valor -- método virtual
   *    @return 
   *    @throws MemException
   */
   protected abstract R read () throws MemException;
}
