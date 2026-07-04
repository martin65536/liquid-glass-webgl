package defpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public abstract class me extends re {
    public static boolean R(Iterable iterable, Object obj) {
        int i;
        iterable.getClass();
        if (iterable instanceof Collection) {
            return ((Collection) iterable).contains(obj);
        }
        if (iterable instanceof List) {
            i = ((List) iterable).indexOf(obj);
        } else {
            Iterator it = iterable.iterator();
            int i2 = 0;
            while (true) {
                if (it.hasNext()) {
                    Object next = it.next();
                    if (i2 >= 0) {
                        if (o20.e(obj, next)) {
                            i = i2;
                            break;
                        }
                        i2++;
                    } else {
                        jc0.H();
                        throw null;
                    }
                } else {
                    i = -1;
                    break;
                }
            }
        }
        if (i < 0) {
            return false;
        }
        return true;
    }

    public static Object S(List list) {
        list.getClass();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static Object T(List list) {
        list.getClass();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static final void U(Iterable iterable, StringBuilder sb, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, gv gvVar) {
        iterable.getClass();
        sb.append(charSequence2);
        int i = 0;
        for (Object obj : iterable) {
            i++;
            if (i > 1) {
                sb.append(charSequence);
            }
            o30.f(sb, obj, gvVar);
        }
        sb.append(charSequence3);
    }

    public static String W(Collection collection, String str, String str2, String str3, gv gvVar, int i) {
        String str4;
        String str5;
        if ((i & 1) != 0) {
            str = ", ";
        }
        String str6 = str;
        if ((i & 2) != 0) {
            str4 = "";
        } else {
            str4 = str2;
        }
        if ((i & 4) != 0) {
            str5 = "";
        } else {
            str5 = str3;
        }
        if ((i & 32) != 0) {
            gvVar = null;
        }
        collection.getClass();
        StringBuilder sb = new StringBuilder();
        U(collection, sb, str6, str4, str5, "...", gvVar);
        return sb.toString();
    }

    public static Object X(List list) {
        list.getClass();
        if (!list.isEmpty()) {
            return list.get(list.size() - 1);
        }
        throw new NoSuchElementException("List is empty.");
    }

    public static Object Y(List list) {
        list.getClass();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static ArrayList Z(Iterable iterable, Object obj) {
        iterable.getClass();
        ArrayList arrayList = new ArrayList(ne.N(iterable));
        boolean z = false;
        for (Object obj2 : iterable) {
            boolean z2 = true;
            if (!z && o20.e(obj2, obj)) {
                z = true;
                z2 = false;
            }
            if (z2) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }

    public static ArrayList a0(Collection collection, Object obj) {
        collection.getClass();
        ArrayList arrayList = new ArrayList(collection.size() + 1);
        arrayList.addAll(collection);
        arrayList.add(obj);
        return arrayList;
    }

    public static ArrayList b0(Collection collection, List list) {
        collection.getClass();
        list.getClass();
        ArrayList arrayList = new ArrayList(list.size() + collection.size());
        arrayList.addAll(collection);
        arrayList.addAll(list);
        return arrayList;
    }

    public static List c0(ArrayList arrayList, Comparator comparator) {
        arrayList.getClass();
        if (arrayList.size() <= 1) {
            return d0(arrayList);
        }
        Object[] array = arrayList.toArray(new Object[0]);
        array.getClass();
        if (array.length > 1) {
            Arrays.sort(array, comparator);
        }
        List asList = Arrays.asList(array);
        asList.getClass();
        return asList;
    }

    public static List d0(Iterable iterable) {
        ArrayList arrayList;
        Object next;
        iterable.getClass();
        boolean z = iterable instanceof Collection;
        er erVar = er.e;
        if (z) {
            Collection collection = (Collection) iterable;
            int size = collection.size();
            if (size != 0) {
                if (size != 1) {
                    return new ArrayList(collection);
                }
                if (iterable instanceof List) {
                    next = ((List) iterable).get(0);
                } else {
                    next = collection.iterator().next();
                }
                return jc0.v(next);
            }
            return erVar;
        }
        iterable.getClass();
        if (iterable instanceof Collection) {
            arrayList = new ArrayList((Collection) iterable);
        } else {
            arrayList = new ArrayList();
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next());
            }
        }
        int size2 = arrayList.size();
        if (size2 != 0) {
            if (size2 != 1) {
                return arrayList;
            }
            return jc0.v(arrayList.get(0));
        }
        return erVar;
    }
}
