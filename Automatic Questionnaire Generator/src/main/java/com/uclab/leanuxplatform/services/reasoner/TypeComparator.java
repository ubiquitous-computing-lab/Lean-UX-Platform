package com.uclab.leanuxplatform.services.reasoner;

//import org.uclab.mm.kcl.edket.krf.util.KRFConditionValueOperator;

public class TypeComparator {

    public static boolean compare(int v1, int v2, KRFConditionValueOperator cvo) {
        boolean matched = false;
        switch (cvo) {

            case EQ:
                matched = v1 == v2;
            break;
            case LT:
                matched = v1 < v2;
            break;
            case GT:
                matched = v1 > v2;
            break;
            case NOT_EQ:
                matched = v1 != v2;
            break;
            case LT_EQ:
                matched = v1 <= v2;
            break;
            case GT_EQ:
                matched = v1 >= v2;
        }

        return matched;
    }
    
    public static boolean compare(String v1, String v2, KRFConditionValueOperator cvo) {
        boolean matched = false;
        switch(cvo){
            
            case EQ:
                matched = v1.equalsIgnoreCase(v2);
            break;
            case NOT_EQ:
                matched = !v1.equalsIgnoreCase(v2);
            break;
            case GT:
            case GT_EQ:
            case LT:
            case LT_EQ:
        }
        return matched;
    }
    
    public static boolean compare(boolean v1, boolean v2, KRFConditionValueOperator cvo) {
        boolean matched = false;
        switch(cvo){
            
            case EQ:
                matched = v1 == v2;
            break;
            case NOT_EQ:
                matched = v1 != v2;
            break;
            case GT:
            case GT_EQ:
            case LT:
            case LT_EQ:
        }
        return matched;
    }
}
