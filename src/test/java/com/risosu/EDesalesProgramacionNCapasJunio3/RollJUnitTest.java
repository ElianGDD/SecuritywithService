
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.RolJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Alien 13
 */
@SpringBootTest
public class RollJUnitTest {
    @Autowired  
    RolJPADAOImplementation rollJPADAOImplementation;
    @Test
    public void GetAllRoll(){
        Result result = rollJPADAOImplementation.GetAllRoll();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que el método GetAll fuera exitoso");
        Assertions.assertNull(result.ex, "No debería haber excepción");
        Assertions.assertNotNull(result.objects, "La lista de objetos no debería ser null");
    
    }
    
}
