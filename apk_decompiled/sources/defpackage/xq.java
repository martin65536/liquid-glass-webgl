package defpackage;

import java.nio.ByteBuffer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class xq {
    public int a = 1;
    public final vc0 b;
    public vc0 c;
    public vc0 d;
    public int e;
    public int f;

    public xq(vc0 vc0Var) {
        this.b = vc0Var;
        this.c = vc0Var;
    }

    public final void a() {
        this.a = 1;
        this.c = this.b;
        this.f = 0;
    }

    public final boolean b() {
        tc0 b = this.c.b.b();
        int a = b.a(6);
        if ((a != 0 && ((ByteBuffer) b.h).get(a + b.e) != 0) || this.e == 65039) {
            return true;
        }
        return false;
    }
}
