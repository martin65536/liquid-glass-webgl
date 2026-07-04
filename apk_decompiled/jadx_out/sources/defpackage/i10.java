package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class i10 extends bd0 implements w21 {
    public f61 s;
    public f61 t;

    public i10() {
        es esVar = o20.s;
        this.s = esVar;
        this.t = esVar;
    }

    public abstract void D0();

    @Override // defpackage.bd0
    public void t0() {
        d20.K(this, "androidx.compose.foundation.layout.ConsumedInsetsProvider", new h10(this, 1));
        D0();
    }

    @Override // defpackage.bd0
    public void v0() {
        this.t = this.s;
        d20.L(this, "androidx.compose.foundation.layout.ConsumedInsetsProvider", new h10(this, 0));
    }

    @Override // defpackage.bd0
    public final void x0() {
        this.s = o20.s;
    }

    @Override // defpackage.w21
    public final Object z() {
        return "androidx.compose.foundation.layout.ConsumedInsetsProvider";
    }
}
