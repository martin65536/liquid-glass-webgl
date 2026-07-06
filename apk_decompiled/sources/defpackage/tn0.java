package defpackage;

import android.os.Handler;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tn0 implements j80 {
    public static final tn0 m = new tn0();
    public int e;
    public int f;
    public Handler i;
    public boolean g = true;
    public boolean h = true;
    public final l80 j = new l80(this, true);
    public final n k = new n(6, this);
    public final j2 l = new j2(18, this);

    public final void a() {
        int i = this.f + 1;
        this.f = i;
        if (i == 1) {
            if (this.g) {
                this.j.d(z70.ON_RESUME);
                this.g = false;
            } else {
                Handler handler = this.i;
                handler.getClass();
                handler.removeCallbacks(this.k);
            }
        }
    }

    @Override // defpackage.j80
    public final l80 f() {
        return this.j;
    }
}
