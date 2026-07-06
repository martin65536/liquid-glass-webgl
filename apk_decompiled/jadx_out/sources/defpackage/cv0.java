package defpackage;

import java.util.Comparator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cv0 implements Comparator {
    public final /* synthetic */ int a = 1;
    public final /* synthetic */ Comparator b;

    public cv0(cv0 cv0Var) {
        this.b = cv0Var;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int i = this.a;
        Comparator comparator = this.b;
        switch (i) {
            case 0:
                int compare = comparator.compare(obj, obj2);
                if (compare == 0) {
                    return z40.T.compare(((su0) obj).c, ((su0) obj2).c);
                }
                return compare;
            default:
                int compare2 = ((cv0) comparator).compare(obj, obj2);
                if (compare2 == 0) {
                    return n20.m(Integer.valueOf(((su0) obj).f), Integer.valueOf(((su0) obj2).f));
                }
                return compare2;
        }
    }

    public cv0(Comparator comparator) {
        this.b = comparator;
    }
}
