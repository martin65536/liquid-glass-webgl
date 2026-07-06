package defpackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zg extends RuntimeException {
    public final pe0 e;
    public final pe0 f;
    public final ge0 g;
    public final int h;

    public zg(pe0 pe0Var, pe0 pe0Var2, ge0 ge0Var, int i, Exception exc) {
        super(exc);
        this.e = pe0Var;
        this.f = pe0Var2;
        this.g = ge0Var;
        this.h = i;
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        List list;
        Collection collection;
        String substring;
        StringBuilder sb = new StringBuilder("\n            |Failed to execute op number ");
        sb.append(this.h);
        sb.append(":\n            |");
        mv0 y = g30.y(new yg(this, null));
        if (!y.hasNext()) {
            list = er.e;
        } else {
            Object next = y.next();
            if (!y.hasNext()) {
                list = jc0.v(next);
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add(next);
                while (y.hasNext()) {
                    arrayList.add(y.next());
                }
                list = arrayList;
            }
        }
        int size = list.size();
        if (50 >= size) {
            collection = me.d0(list);
        } else {
            ArrayList arrayList2 = new ArrayList(50);
            if (list instanceof RandomAccess) {
                for (int i = size - 50; i < size; i++) {
                    arrayList2.add(list.get(i));
                }
            } else {
                ListIterator listIterator = list.listIterator(size - 50);
                while (listIterator.hasNext()) {
                    arrayList2.add(listIterator.next());
                }
            }
            collection = arrayList2;
        }
        sb.append(me.W(collection, "\n", null, null, null, 62));
        sb.append("\n            ");
        String sb2 = sb.toString();
        if (!uy0.B("|")) {
            List C = uy0.C(sb2);
            int length = sb2.length();
            C.size();
            int size2 = C.size() - 1;
            ArrayList arrayList3 = new ArrayList();
            int i2 = 0;
            for (Object obj : C) {
                int i3 = i2 + 1;
                if (i2 >= 0) {
                    String str = (String) obj;
                    if ((i2 == 0 || i2 == size2) && uy0.B(str)) {
                        str = null;
                    } else {
                        int length2 = str.length();
                        int i4 = 0;
                        while (true) {
                            if (i4 < length2) {
                                if (!k81.A(str.charAt(i4))) {
                                    break;
                                }
                                i4++;
                            } else {
                                i4 = -1;
                                break;
                            }
                        }
                        if (i4 == -1 || !str.startsWith("|", i4)) {
                            substring = null;
                        } else {
                            substring = str.substring("|".length() + i4);
                        }
                        if (substring != null) {
                            str = substring;
                        }
                    }
                    if (str != null) {
                        arrayList3.add(str);
                    }
                    i2 = i3;
                } else {
                    jc0.H();
                    throw null;
                }
            }
            StringBuilder sb3 = new StringBuilder(length);
            me.U(arrayList3, sb3, "\n", "", "", "...", null);
            return sb3.toString();
        }
        v7.m("marginPrefix must be non-blank string.");
        return null;
    }
}
