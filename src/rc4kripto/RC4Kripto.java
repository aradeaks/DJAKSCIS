/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rc4kripto;

/**
 *
 * @author Aradea Krisnaraga & Daniel Januar
 */
public class RC4Kripto {
    private int[][] S;

    public RC4Kripto() {
        S = new int[26][10];
    }

    public int[][] getS() {
        return S;
    }
    
    public int[] encryption(int[] plaintext, byte[] key){
        
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
}
