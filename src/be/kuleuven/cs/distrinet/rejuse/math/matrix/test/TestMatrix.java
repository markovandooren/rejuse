package be.kuleuven.cs.distrinet.rejuse.math.matrix.test;

import org.junit.Before;
import org.junit.Test;

import be.kuleuven.cs.distrinet.rejuse.junit.CVSRevision;
import be.kuleuven.cs.distrinet.rejuse.junit.JutilTest;
import be.kuleuven.cs.distrinet.rejuse.math.matrix.HouseholderQRDecomposer;
import be.kuleuven.cs.distrinet.rejuse.math.matrix.Matrix;
import be.kuleuven.cs.distrinet.rejuse.math.matrix.QRDecomposition;

/**
 * @path    $Source$
 * @date    $Date$
 * @state   $State$
 * @author  Marko van Dooren
 * @release $Name$
 */

public class TestMatrix {

    //  public TestMatrix(String name) {
    //    super(name, new CVSRevision("1.19"));
    //  }

    private Matrix A;  
    private Matrix B;  

    @Before
    public void setUp() {
        A = new Matrix(4,3);
        A.setElementAt(0,0,3);
        A.setElementAt(1,0,4);
        A.setElementAt(2,0,0);
        A.setElementAt(3,0,0);
        A.setElementAt(0,1,3);
        A.setElementAt(1,1,4);
        A.setElementAt(2,1,6);
        A.setElementAt(3,1,8);
        A.setElementAt(0,2,2);
        A.setElementAt(1,2,1);
        A.setElementAt(2,2,2);
        A.setElementAt(3,2,1);

        B = Matrix.unity(4);
    }

    public void test() {
        testQR(A);
        testQR(B);
    } 

    public void tearDown() {
        //nothing actually
    }

    private void testQR(Matrix matrix) {
        QRDecomposition qr = new HouseholderQRDecomposer().decompose(matrix);
        Matrix product= qr.Q().times(qr.R());
    }

    @Test
    public void testFast() {
        Matrix first = bigWhopper(1000);
        Matrix second = bigWhopper(1000);
        int times = 1000;
        for(int i=0;i<times;i++) {
            first.add(second);
        }
    }

    @Test
    public void testSlow() {
        Matrix first = bigWhopper(1000);
        Matrix second = bigWhopper(1000);
        int times = 1000;
        for(int i=0;i<times;i++) {
            first.addSlow(second);
        }
    }

    private Matrix bigWhopper(int size) {
        Matrix result = new Matrix(size, size);
        for(int i=0; i<size;i++) {
            for(int j=0; j<size;j++) {
                result.setElementAt(i, j, i+j);
            }
        }
        return result;
    }

}


