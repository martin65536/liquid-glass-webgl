package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class bc0 extends dc0 implements Iterator, q30 {
    public final /* synthetic */ int i;

    public bc0(ec0 ec0Var, int i) {
        this.i = i;
        ec0Var.getClass();
        this.h = ec0Var;
        this.f = -1;
        this.g = ec0Var.l;
        c();
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.i) {
            case 0:
                b();
                int i = this.e;
                ec0 ec0Var = (ec0) this.h;
                if (i < ec0Var.j) {
                    this.e = i + 1;
                    this.f = i;
                    cc0 cc0Var = new cc0(ec0Var, i);
                    c();
                    return cc0Var;
                }
                v7.n();
                return null;
            case 1:
                b();
                int i2 = this.e;
                ec0 ec0Var2 = (ec0) this.h;
                if (i2 < ec0Var2.j) {
                    this.e = i2 + 1;
                    this.f = i2;
                    Object obj = ec0Var2.e[i2];
                    c();
                    return obj;
                }
                v7.n();
                return null;
            default:
                b();
                int i3 = this.e;
                ec0 ec0Var3 = (ec0) this.h;
                if (i3 < ec0Var3.j) {
                    this.e = i3 + 1;
                    this.f = i3;
                    Object[] objArr = ec0Var3.f;
                    objArr.getClass();
                    Object obj2 = objArr[this.f];
                    c();
                    return obj2;
                }
                v7.n();
                return null;
        }
    }
}
