package defpackage;

import java.util.concurrent.CancellationException;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ib {
    public final ef0 a;

    public ib(int i) {
        switch (i) {
            case 1:
                this.a = new ef0(new u50[16]);
                return;
            default:
                this.a = new ef0(new yi[16]);
                return;
        }
    }

    public void a(CancellationException cancellationException) {
        ef0 ef0Var = this.a;
        int i = ef0Var.g;
        nc[] ncVarArr = new nc[i];
        for (int i2 = 0; i2 < i; i2++) {
            ncVarArr[i2] = ((yi) ef0Var.e[i2]).b;
        }
        for (int i3 = 0; i3 < i; i3++) {
            ncVarArr[i3].x(cancellationException);
        }
        if (ef0Var.g == 0) {
            return;
        }
        t00.c("uncancelled requests present");
    }

    public void b() {
        ef0 ef0Var = this.a;
        y10 K = n30.K(0, ef0Var.g);
        int i = K.e;
        int i2 = K.f;
        if (i <= i2) {
            while (true) {
                ((yi) ef0Var.e[i]).b.u(x31.a);
                if (i == i2) {
                    break;
                } else {
                    i++;
                }
            }
        }
        ef0Var.g();
    }
}
