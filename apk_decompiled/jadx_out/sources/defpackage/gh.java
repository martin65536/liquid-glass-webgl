package defpackage;

import android.graphics.RenderEffect;
import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class gh {
    public Object a;

    public gh(int i) {
        switch (i) {
            case 2:
                this.a = new Object();
                return;
            default:
                this.a = new ArrayList();
                return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x003a, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean a(int r8, defpackage.dw r9, java.lang.Object r10) {
        /*
            r7 = this;
            java.util.ArrayList r0 = r9.a
            r1 = 1
            if (r0 != 0) goto La
            r10 = 0
            r7.b(r8, r9, r10)
            return r1
        La:
            int r2 = r0.size()
            r3 = 0
            r4 = r3
        L10:
            if (r4 >= r2) goto L3a
            java.lang.Object r5 = r0.get(r4)
            boolean r6 = r5 instanceof defpackage.wv
            if (r6 == 0) goto L21
            if (r5 == r10) goto L1d
            goto L32
        L1d:
            r7.b(r3, r9, r5)
            return r1
        L21:
            boolean r6 = r5 instanceof defpackage.dw
            if (r6 == 0) goto L35
            r6 = r5
            dw r6 = (defpackage.dw) r6
            boolean r6 = r7.a(r8, r6, r10)
            if (r6 == 0) goto L32
            r7.b(r3, r9, r5)
            return r1
        L32:
            int r4 = r4 + 1
            goto L10
        L35:
            java.lang.String r7 = "Unexpected child source info "
            defpackage.v7.e(r5, r7)
        L3a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gh.a(int, dw, java.lang.Object):boolean");
    }

    public void b(int i, dw dwVar, Object obj) {
        ((ArrayList) this.a).add(new hh(i, null, null));
    }

    public RenderEffect c() {
        RenderEffect renderEffect = (RenderEffect) this.a;
        if (renderEffect == null) {
            RenderEffect f = f();
            this.a = f;
            return f;
        }
        return renderEffect;
    }

    public abstract void d(jv0 jv0Var);

    public abstract void e();

    public abstract RenderEffect f();

    public abstract void g();

    public void h(int i, Object obj, dw dwVar, Object obj2) {
        if (!o20.e(obj, ph.a)) {
            return;
        }
        b(i, dwVar, null);
    }

    public abstract gv i(jv0 jv0Var);

    public abstract void j(ed edVar);
}
