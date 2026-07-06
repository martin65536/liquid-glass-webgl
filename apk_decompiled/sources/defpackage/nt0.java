package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nt0 implements au0 {
    public static final c4 j = new c4(21, new us0(19), new ts0(25));
    public final fk0 a;
    public float f;
    public final ym h;
    public final ym i;
    public final fk0 b = new fk0(0);
    public final fk0 c = new fk0(0);
    public final je0 d = new je0();
    public final fk0 e = new fk0(Integer.MAX_VALUE);
    public final dm g = new dm(new l(14, this));

    public nt0(int i) {
        this.a = new fk0(i);
        final int i2 = 0;
        this.h = n30.r(new vu(this) { // from class: mt0
            public final /* synthetic */ nt0 f;

            {
                this.f = this;
            }

            @Override // defpackage.vu
            public final Object a() {
                int i3 = i2;
                boolean z = false;
                nt0 nt0Var = this.f;
                switch (i3) {
                    case 0:
                        if (nt0Var.a.g() < nt0Var.e.g()) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        if (nt0Var.a.g() > 0) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
        final int i3 = 1;
        this.i = n30.r(new vu(this) { // from class: mt0
            public final /* synthetic */ nt0 f;

            {
                this.f = this;
            }

            @Override // defpackage.vu
            public final Object a() {
                int i32 = i3;
                boolean z = false;
                nt0 nt0Var = this.f;
                switch (i32) {
                    case 0:
                        if (nt0Var.a.g() < nt0Var.e.g()) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    default:
                        if (nt0Var.a.g() > 0) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                }
            }
        });
    }

    @Override // defpackage.au0
    public final boolean a() {
        return ((Boolean) this.i.getValue()).booleanValue();
    }

    @Override // defpackage.au0
    public final boolean b() {
        return this.g.b();
    }

    @Override // defpackage.au0
    public final boolean c() {
        return ((Boolean) this.h.getValue()).booleanValue();
    }

    @Override // defpackage.au0
    public final Object d(gf0 gf0Var, kv kvVar, jj jjVar) {
        Object d = this.g.d(gf0Var, kvVar, jjVar);
        if (d == ik.e) {
            return d;
        }
        return x31.a;
    }

    @Override // defpackage.au0
    public final float e(float f) {
        return this.g.e(f);
    }
}
