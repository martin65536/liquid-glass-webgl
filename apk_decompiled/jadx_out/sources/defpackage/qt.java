package defpackage;

import java.util.Comparator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qt implements Comparator {
    public static final qt b = new qt(0);
    public static final qt c = new qt(1);
    public static final qt d = new qt(2);
    public static final qt e = new qt(3);
    public static final qt f = new qt(4);
    public final /* synthetic */ int a;

    public /* synthetic */ qt(int i) {
        this.a = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.lang.Object[], java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.lang.Object[], java.lang.Object] */
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                pt ptVar = (pt) obj;
                pt ptVar2 = (pt) obj2;
                if (n20.B(ptVar) && n20.B(ptVar2)) {
                    z40 E = k81.E(ptVar);
                    z40 E2 = k81.E(ptVar2);
                    if (o20.e(E, E2)) {
                        return 0;
                    }
                    z40[] z40VarArr = new z40[16];
                    int i = 0;
                    while (E != null) {
                        int i2 = i + 1;
                        if (z40VarArr.length < i2) {
                            int length = z40VarArr.length;
                            ?? r4 = new Object[Math.max(i2, length * 2)];
                            System.arraycopy(z40VarArr, 0, r4, 0, length);
                            z40VarArr = r4;
                        }
                        if (i != 0) {
                            System.arraycopy(z40VarArr, 0, z40VarArr, 0 + 1, i + 0);
                        }
                        z40VarArr[0] = E;
                        i++;
                        E = E.s();
                    }
                    z40[] z40VarArr2 = new z40[16];
                    int i3 = 0;
                    while (E2 != null) {
                        int i4 = i3 + 1;
                        if (z40VarArr2.length < i4) {
                            int length2 = z40VarArr2.length;
                            ?? r42 = new Object[Math.max(i4, length2 * 2)];
                            System.arraycopy(z40VarArr2, 0, r42, 0, length2);
                            z40VarArr2 = r42;
                        }
                        if (i3 != 0) {
                            System.arraycopy(z40VarArr2, 0, z40VarArr2, 0 + 1, i3 + 0);
                        }
                        z40VarArr2[0] = E2;
                        i3++;
                        E2 = E2.s();
                    }
                    int min = Math.min(i - 1, i3 - 1);
                    if (min >= 0) {
                        int i5 = 0;
                        while (o20.e(z40VarArr[i5], z40VarArr2[i5])) {
                            if (i5 != min) {
                                i5++;
                            }
                        }
                        return o20.i(z40VarArr[i5].t(), z40VarArr2[i5].t());
                    }
                    v7.o("Could not find a common ancestor between the two FocusModifiers.");
                    return 0;
                }
                if (n20.B(ptVar)) {
                    return -1;
                }
                if (!n20.B(ptVar2)) {
                    return 0;
                }
                return 1;
            case 1:
                wo0 h = ((su0) obj).h();
                wo0 h2 = ((su0) obj2).h();
                int compare = Float.compare(h.a, h2.a);
                if (compare == 0) {
                    int compare2 = Float.compare(h.b, h2.b);
                    if (compare2 == 0) {
                        int compare3 = Float.compare(h.d, h2.d);
                        if (compare3 == 0) {
                            return Float.compare(h.c, h2.c);
                        }
                        return compare3;
                    }
                    return compare2;
                }
                return compare;
            case 2:
                z40 z40Var = (z40) obj;
                z40 z40Var2 = (z40) obj2;
                int i6 = o20.i(z40Var2.s, z40Var.s);
                if (i6 == 0) {
                    return o20.i(z40Var.hashCode(), z40Var2.hashCode());
                }
                return i6;
            case 3:
                wo0 h3 = ((su0) obj).h();
                wo0 h4 = ((su0) obj2).h();
                int compare4 = Float.compare(h4.c, h3.c);
                if (compare4 == 0) {
                    int compare5 = Float.compare(h3.b, h4.b);
                    if (compare5 == 0) {
                        int compare6 = Float.compare(h3.d, h4.d);
                        if (compare6 == 0) {
                            return Float.compare(h4.a, h3.a);
                        }
                        return compare6;
                    }
                    return compare5;
                }
                return compare4;
            case 4:
                xj0 xj0Var = (xj0) obj;
                xj0 xj0Var2 = (xj0) obj2;
                int compare7 = Float.compare(((wo0) xj0Var.e).b, ((wo0) xj0Var2.e).b);
                if (compare7 == 0) {
                    return Float.compare(((wo0) xj0Var.e).d, ((wo0) xj0Var2.e).d);
                }
                return compare7;
            case 5:
                return n20.m(Integer.valueOf(((k7) obj).b), Integer.valueOf(((k7) obj2).b));
            case 6:
                return n20.m(Integer.valueOf(((k7) obj).b), Integer.valueOf(((k7) obj2).b));
            case 7:
                z40 z40Var3 = (z40) obj;
                z40 z40Var4 = (z40) obj2;
                int i7 = o20.i(z40Var3.s, z40Var4.s);
                if (i7 == 0) {
                    return o20.i(z40Var3.hashCode(), z40Var4.hashCode());
                }
                return i7;
            case 8:
                return n20.m(Integer.valueOf(((qm) obj).e), Integer.valueOf(((qm) obj2).e));
            default:
                return n20.m(Integer.valueOf(((qm) obj2).e), Integer.valueOf(((qm) obj).e));
        }
    }
}
