package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class f70 {
    public final he0 a;
    public final c70 b;
    public final j60 c;
    public final long d;
    public final /* synthetic */ j60 e;
    public final /* synthetic */ int f;
    public final /* synthetic */ int g;
    public final /* synthetic */ z9 h;
    public final /* synthetic */ int i;
    public final /* synthetic */ int j;
    public final /* synthetic */ long k;
    public final /* synthetic */ m70 l;

    public f70(long j, c70 c70Var, j60 j60Var, int i, int i2, z9 z9Var, int i3, int i4, long j2, m70 m70Var) {
        this.e = j60Var;
        this.f = i;
        this.g = i2;
        this.h = z9Var;
        this.i = i3;
        this.j = i4;
        this.k = j2;
        this.l = m70Var;
        he0 he0Var = u10.a;
        this.a = new he0();
        this.b = c70Var;
        this.c = j60Var;
        this.d = ti.b(si.h(j), Integer.MAX_VALUE, 5);
    }

    public final i70 a(int i, long j) {
        long j2;
        List list;
        c70 c70Var = this.b;
        Object d = c70Var.d(i);
        Object b = c70Var.b(i);
        he0 he0Var = this.a;
        List list2 = (List) he0Var.b(i);
        int i2 = 0;
        if (list2 != null) {
            j2 = j;
            list = list2;
        } else {
            j60 j60Var = this.c;
            c70 c70Var2 = j60Var.g;
            he0 he0Var2 = j60Var.h;
            List list3 = (List) he0Var2.b(i);
            if (list3 == null) {
                Object d2 = c70Var2.d(i);
                list3 = j60Var.f.L(j60Var.e.a(i, d2, c70Var2.b(i)), d2);
                he0Var2.h(i, list3);
            }
            int size = list3.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i3 = 0; i3 < size; i3++) {
                arrayList.add(((kc0) list3.get(i3)).v(j));
            }
            j2 = j;
            he0Var.h(i, arrayList);
            list = arrayList;
        }
        if (i != this.f - 1) {
            i2 = this.g;
        }
        return new i70(i, list, this.h, this.e.f.getLayoutDirection(), this.i, this.j, i2, this.k, d, b, this.l.n, j2);
    }
}
