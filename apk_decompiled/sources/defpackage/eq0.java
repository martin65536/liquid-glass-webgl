package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class eq0 {
    public static final dq0 a = new Object();
    public static final qy0 b = new do0(new c2(26));

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10, types: [java.util.Collection] */
    /* JADX WARN: Type inference failed for: r5v11, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r5v12, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r5v13, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v17, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r5v6, types: [er] */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [java.util.Collection] */
    /* JADX WARN: Type inference failed for: r5v9 */
    public static final fq0 a(zp zpVar, cq0 cq0Var) {
        cq0Var.getClass();
        List d0 = me.d0(zpVar.b);
        ArrayList arrayList = new ArrayList();
        Iterator it = d0.iterator();
        while (it.hasNext()) {
            ((fq0) it.next()).getClass();
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((fq0) obj).getClass();
        }
        if (arrayList2.isEmpty()) {
            arrayList2 = new ArrayList();
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                ((fq0) obj2).getClass();
                arrayList2.add(obj2);
            }
            if (arrayList2.isEmpty()) {
                arrayList2 = new ArrayList();
                for (Object obj3 : d0) {
                    ((fq0) obj3).getClass();
                    arrayList2.add(obj3);
                }
            }
        }
        if (arrayList2.size() == 1) {
            return (fq0) me.S(arrayList2);
        }
        ArrayList arrayList3 = new ArrayList();
        int size3 = arrayList2.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj4 = arrayList2.get(i4);
            i4++;
            ((fq0) obj4).getClass();
        }
        if (arrayList3.isEmpty()) {
            arrayList3 = new ArrayList();
            int size4 = arrayList2.size();
            int i5 = 0;
            while (i5 < size4) {
                Object obj5 = arrayList2.get(i5);
                i5++;
                ((fq0) obj5).getClass();
                arrayList3.add(obj5);
            }
        }
        if (arrayList3.size() == 1) {
            return (fq0) me.S(arrayList3);
        }
        int i6 = cq0Var.d.e;
        ArrayList arrayList4 = new ArrayList();
        mr mrVar = qm.n;
        Iterator it2 = mrVar.iterator();
        while (it2.hasNext()) {
            Object next = it2.next();
            if (((qm) next).e >= i6) {
                arrayList4.add(next);
            }
        }
        ?? r5 = er.e;
        for (qm qmVar : me.c0(arrayList4, new qt(8))) {
            r5 = new ArrayList();
            int size5 = arrayList3.size();
            int i7 = 0;
            while (i7 < size5) {
                Object obj6 = arrayList3.get(i7);
                i7++;
                ((fq0) obj6).getClass();
            }
            if (!r5.isEmpty()) {
                break;
            }
        }
        if (r5.isEmpty()) {
            ArrayList Z = me.Z(mrVar, qm.g);
            ArrayList arrayList5 = new ArrayList();
            int size6 = Z.size();
            int i8 = 0;
            while (i8 < size6) {
                Object obj7 = Z.get(i8);
                i8++;
                if (((qm) obj7).e < i6) {
                    arrayList5.add(obj7);
                }
            }
            for (qm qmVar2 : me.c0(arrayList5, new qt(9))) {
                r5 = new ArrayList();
                int size7 = arrayList3.size();
                int i9 = 0;
                while (i9 < size7) {
                    Object obj8 = arrayList3.get(i9);
                    i9++;
                    ((fq0) obj8).getClass();
                }
                if (!r5.isEmpty()) {
                    break;
                }
            }
            if (r5.isEmpty()) {
                r5 = new ArrayList();
                int size8 = arrayList3.size();
                int i10 = 0;
                while (i10 < size8) {
                    Object obj9 = arrayList3.get(i10);
                    i10++;
                    ((fq0) obj9).getClass();
                    r5.add(obj9);
                }
                if (r5.isEmpty()) {
                    r5 = new ArrayList();
                    int size9 = arrayList3.size();
                    while (i < size9) {
                        Object obj10 = arrayList3.get(i);
                        i++;
                        ((fq0) obj10).getClass();
                    }
                }
            }
        }
        List list = r5;
        if (list.size() == 1) {
            return (fq0) me.S(list);
        }
        boolean isEmpty = list.isEmpty();
        String str = zpVar.a;
        if (isEmpty) {
            throw new IllegalStateException(("Resource with ID='" + str + "' not found").toString());
        }
        throw new IllegalStateException(("Resource with ID='" + str + "' has more than one file: " + me.W(list, null, null, null, new pb(16), 31)).toString());
    }
}
