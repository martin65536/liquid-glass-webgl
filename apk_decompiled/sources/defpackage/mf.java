package defpackage;

import java.util.Comparator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class mf implements Comparator {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ mf(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int i = this.a;
        Object obj3 = this.b;
        switch (i) {
            case 0:
                for (gv gvVar : (gv[]) obj3) {
                    int m = n20.m((Comparable) gvVar.e(obj), (Comparable) gvVar.e(obj2));
                    if (m != 0) {
                        return m;
                    }
                }
                return 0;
            default:
                return ((Number) ((kv) obj3).d(obj, obj2)).intValue();
        }
    }
}
