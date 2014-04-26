/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rc4kripto;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author daniel.januar
 */
public class RC4Kripto {
    private int[][] S;

    public RC4Kripto() {
        S = new int[26][10];
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RC4Kripto rc = new RC4Kripto();
        int[] m = new int[]{0xA5,0xE3,0x89,0xF1};
        byte[] k = "PEMILU".getBytes();
        rc.permuteS(k);
        int[] enc = rc.encryption(m, k);
        rc.permuteS(k);
        int[] dec = rc.encryption(enc, k);
        String hasil = "";
        for (int i = 0; i < dec.length; i++) {
            hasil += Integer.toHexString(dec[i]);
        }
        System.out.println(hasil);
    }
    
    public int[] encryption(int[] plaintext, byte[] key){
//        String str = new String(plaintext);
//        System.out.println("Plaintext: " + str);
//        
//        str = new String(key);
//        System.out.println("Key: " + str);
//        
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                System.out.print(S[i][j] + "|");
            }
            System.out.println();
        }
        
        int[] res = new int[plaintext.length];
        int jj = 0;
        for(int ii = 0; ii< plaintext.length;){
            ii = (ii+1) % 256;
            int[] index = getIndexS(ii);
            jj = (jj+ S[index[0]][index[1]]) % 256;
            int[] indexjj = getIndexS(jj);
            S[index[0]][index[1]] ^= S[indexjj[0]][indexjj[1]];
            S[indexjj[0]][indexjj[1]] ^= S[index[0]][index[1]];
            S[index[0]][index[1]] ^= S[indexjj[0]][indexjj[1]];
            int t = (S[index[0]][index[1]] + S[indexjj[0]][indexjj[1]]) % 256;
            index = getIndexS(t);
            System.out.println("S[t]: " + S[index[0]][index[1]]);
            res[ii-1] = plaintext[ii-1] ^ S[index[0]][index[1]];
        }
        
        return res;
    }
    
    public int[] getIndexS(int index){
        return new int[]{index/10,index % 10};
    }
    
    public void permuteS(byte[] key){
        int [][] T = new int[26][10];
        for(int ii=0;ii<256;ii++){
            int[] index = getIndexS(ii);
            S[index[0]][index[1]] = ii;
            T[index[0]][index[1]] = key[ii % key.length];
        }
        
        int jj = 0;
        for(int ii=0;ii<256;ii++){
            int[] index = getIndexS(ii);
            jj = (jj + S[index[0]][index[1]] + T[index[0]][index[1]]) % 256;
            int[] indexjj = getIndexS(jj);
            S[index[0]][index[1]] ^= S[indexjj[0]][indexjj[1]];
            S[indexjj[0]][indexjj[1]] ^= S[index[0]][index[1]];
            S[index[0]][index[1]] ^= S[indexjj[0]][indexjj[1]];
        }
    }
    
    public int[] decryption(int[] ciphertext, byte[] key){
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                System.out.print(S[i][j] + "|");
            }
            System.out.println();
        }
        int[] res = new int[ciphertext.length];
        int jj = 0;
        for(int ii = 0; ii< ciphertext.length;){
            ii = (ii+1) % 256;
            int[] index = getIndexS(ii);
            System.out.println("i:"+ii);
            System.out.println("S[i]: " + S[index[0]][index[1]]);
            jj = (jj+ S[index[0]][index[1]]) % 256;
            int[] indexjj = getIndexS(jj);
            System.out.println("j:"+jj);
            System.out.println("S[j]: " + S[indexjj[0]][indexjj[1]]);
            S[index[0]][index[1]] ^= S[indexjj[0]][indexjj[1]];
            S[indexjj[0]][indexjj[1]] ^= S[index[0]][index[1]];
            S[index[0]][index[1]] ^= S[indexjj[0]][indexjj[1]];
            int t = (S[index[0]][index[1]] + S[indexjj[0]][indexjj[1]]) % 256;
            index = getIndexS(t);
            System.out.println("S[t]: " + S[index[0]][index[1]]);
            res[ii-1] = ciphertext[ii-1] ^ S[index[0]][index[1]];
        }
        
        return res;
    }
}
