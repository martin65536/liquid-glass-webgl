package defpackage;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c9 {
    public Object a;
    public boolean b;
    public final Object c;
    public Object d;

    public c9(int i) {
        switch (i) {
            case 1:
                this.c = new Object();
                this.a = new ArrayList();
                this.d = new ArrayList();
                this.b = true;
                return;
            default:
                this.a = new ArrayList();
                this.b = false;
                this.c = new CopyOnWriteArrayList();
                return;
        }
    }

    public boolean a() {
        boolean z;
        synchronized (this.c) {
            z = this.b;
        }
        return z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public c9(vg vgVar) {
        this(0);
        this.d = vgVar;
    }

    public c9(boolean z) {
        this.b = z;
        this.c = dl.a(0.0f, 0.01f);
        this.a = new ArrayList();
    }

    public c9(e60 e60Var, hz0 hz0Var, in0 in0Var) {
        this.a = e60Var;
        this.c = hz0Var;
        this.d = in0Var;
        this.b = true;
    }
}
