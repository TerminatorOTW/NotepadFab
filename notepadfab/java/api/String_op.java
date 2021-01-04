
// Developed by the TerminatorOTW

package notepadfab.java.api;

import java.util.ArrayList;
import java.util.List;

public class String_op {
    
    public int[] getAllMatchesIndexRange(String text, String value, boolean matchCase) {
        
        int[] indexRanges;
        List<String> iR = new ArrayList<>();
        
        for (int offset=0; offset < text.length();) {
            int[] range = getIndexRange(offset, text, value, matchCase);
            if (range[0] != -1 || range[1] != -1) {
                iR.add(range[0] + "");
                iR.add(range[1] + "");
                offset = range[1];
            } else {
                break;
            }
        }
        
        final int matches = iR.size();
        indexRanges = new int[matches];
        
        for (int i=0; i < matches; i++) {
            indexRanges[i] = Integer.parseInt(iR.get(i));
        }
        
        return indexRanges;
    }
    
    public int[] getIndexRange(String text, String value, boolean matchCase) {
        
        if (value.isEmpty())
            return new int[] {-1, -1};
        
        if (!matchCase) {
            text = text.toLowerCase();
            value = value.toLowerCase();
        }
        
        int[] range = new int[] {-1, -1};
        
        if (text.isEmpty() || value.isEmpty()) {
            return range;
        }
        
        final int length = text.length();
        final int valueLength = value.length();
        final char firstChar = value.charAt(0);
        
        for (int i = 0; i < length; i++) {
            
            if (text.charAt(i) == firstChar) {
                int matched = 1;
                for (int j = 1; j < valueLength; j++) {
                    if ((i + j) < length && text.charAt(i + j) != value.charAt(j))
                        break;
                    matched++;
                }
                
                if (matched == valueLength) {
                    range[0] = i;
                    range[1] = i + (valueLength - 1);
                    break;
                }
                
            }
            
        }
        
        return range;
    }
    
    public int[] getIndexRange(int start, String text, String value, boolean matchCase) {
        
        if (start > text.length() || value.isEmpty())
            return new int[] {-1, -1};
        
        if (!matchCase) {
            text = text.toLowerCase();
            value = value.toLowerCase();
        }
        
        int[] range = new int[] {-1, -1};
        
        if (text.isEmpty() || value.isEmpty()) {
            return range;
        }
        
        final int length = text.length();
        final int valueLength = value.length();
        final char firstChar = value.charAt(0);
        
        for (int i = start; i < length; i++) {
            
            if (text.charAt(i) == firstChar) {
                int matched = 1;
                for (int j = 1; j < valueLength; j++) {
                    if ((i + j) < length && text.charAt(i + j) != value.charAt(j))
                        break;
                    matched++;
                }
                
                if (matched == valueLength) {
                    range[0] = i;
                    range[1] = i + (valueLength - 1);
                    break;
                }
                
            }
            
        }
        
        return range;
    }
    
    public String replaceStringAtPos(int start, int end, String text, String value) {
        
        if (start < 0 || end > text.length() || start > end)
            return text;
        
        String text1 = text.substring(0, start);
        String text2 = text.substring(end, text.length());
        
        return text1 + value + text2;
    }
    
    public String reverseString(String string) {
        
        if (string.isEmpty())
            return string;
        
        String text = "";
        final int length = string.length();
        for (int i = (length - 1); i >= 0; i--) {
            text += string.charAt(i);
        }
        
        return text;
    }
    
}
