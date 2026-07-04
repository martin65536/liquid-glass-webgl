package defpackage;

import android.os.Trace;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hl0 {
    public final yh a;
    public final th b;
    public final bw c;
    public final kv d;
    public final boolean e;
    public final r7 f;
    public final Object g;
    public final AtomicReference h = new AtomicReference(jl0.g);
    public long i = m20.o();
    public we0 j;
    public final mp0 k;
    public final r7 l;

    public hl0(yh yhVar, th thVar, bw bwVar, ye0 ye0Var, kv kvVar, boolean z, r7 r7Var, Object obj) {
        this.a = yhVar;
        this.b = thVar;
        this.c = bwVar;
        this.d = kvVar;
        this.e = z;
        this.f = r7Var;
        this.g = obj;
        we0 we0Var = at0.a;
        we0Var.getClass();
        this.j = we0Var;
        mp0 mp0Var = new mp0();
        mp0Var.g(ye0Var, bwVar.z());
        this.k = mp0Var;
        this.l = new r7(r7Var.h);
    }

    public final void a() {
        AtomicReference atomicReference = this.h;
        try {
            switch (((jl0) atomicReference.get()).ordinal()) {
                case 0:
                    throw new IllegalStateException("The paused composition is invalid because of a previous exception");
                case 1:
                    throw new IllegalStateException("The paused composition has been cancelled");
                case 2:
                case 3:
                case 4:
                    throw new IllegalStateException("The paused composition has not completed yet");
                case 5:
                    b();
                    jl0 jl0Var = jl0.j;
                    jl0 jl0Var2 = jl0.k;
                    while (!atomicReference.compareAndSet(jl0Var, jl0Var2)) {
                        if (atomicReference.get() != jl0Var) {
                            cn0.b("Unexpected state change from: " + jl0Var + " to: " + jl0Var2 + '.');
                            return;
                        }
                    }
                    return;
                case 6:
                    throw new IllegalStateException("The paused composition has already been applied");
                default:
                    throw new RuntimeException();
            }
        } catch (Exception e) {
            atomicReference.set(jl0.e);
            throw e;
        }
    }

    public final void b() {
        Trace.beginSection("PausedComposition:applyChanges");
        try {
            synchronized (this.g) {
                try {
                    this.l.A(this.f, this.k);
                    this.k.c();
                    this.k.d();
                } finally {
                    this.k.b();
                    this.a.u = null;
                }
            }
        } finally {
            Trace.endSection();
        }
    }

    public final boolean c() {
        if (((jl0) this.h.get()).compareTo(jl0.j) >= 0) {
            return true;
        }
        return false;
    }

    public final void d() {
        jl0 jl0Var;
        jl0 jl0Var2;
        boolean z;
        while (true) {
            AtomicReference atomicReference = this.h;
            jl0Var = jl0.h;
            jl0Var2 = jl0.j;
            if (atomicReference.compareAndSet(jl0Var, jl0Var2)) {
                z = true;
                break;
            } else if (atomicReference.get() != jl0Var) {
                z = false;
                break;
            }
        }
        if (!z) {
            cn0.b("Unexpected state change from: " + jl0Var + " to: " + jl0Var2 + '.');
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:5:0x001a. Please report as an issue. */
    public final boolean e(iw0 iw0Var) {
        jl0 jl0Var = jl0.i;
        AtomicReference atomicReference = this.h;
        try {
            int ordinal = ((jl0) atomicReference.get()).ordinal();
            jl0 jl0Var2 = jl0.h;
            yh yhVar = this.a;
            th thVar = this.b;
            switch (ordinal) {
                case 0:
                    throw new IllegalStateException("The paused composition is invalid because of a previous exception");
                case 1:
                    throw new IllegalStateException("The paused composition has been cancelled");
                case 2:
                    bw bwVar = this.c;
                    boolean z = this.e;
                    if (z) {
                        bwVar.z = 0;
                        bwVar.y = true;
                    }
                    try {
                        this.j = thVar.b(yhVar, iw0Var, this.d);
                        jl0 jl0Var3 = jl0.g;
                        while (true) {
                            if (!atomicReference.compareAndSet(jl0Var3, jl0Var2)) {
                                if (atomicReference.get() != jl0Var3) {
                                    cn0.b("Unexpected state change from: " + jl0Var3 + " to: " + jl0Var2 + '.');
                                }
                            }
                        }
                        if (this.j.g()) {
                            d();
                        }
                        return c();
                    } finally {
                        if (z) {
                            bwVar.s();
                        }
                    }
                case 3:
                    while (true) {
                        if (!atomicReference.compareAndSet(jl0Var2, jl0Var)) {
                            if (atomicReference.get() != jl0Var2) {
                                cn0.b("Unexpected state change from: " + jl0Var2 + " to: " + jl0Var + '.');
                            }
                        }
                    }
                    long j = this.i;
                    try {
                        this.i = m20.o();
                        this.j = thVar.n(yhVar, iw0Var, this.j);
                        this.i = j;
                        while (true) {
                            if (!atomicReference.compareAndSet(jl0Var, jl0Var2)) {
                                if (atomicReference.get() != jl0Var) {
                                    cn0.b("Unexpected state change from: " + jl0Var + " to: " + jl0Var2 + '.');
                                }
                            }
                        }
                        if (this.j.g()) {
                            d();
                        }
                        return c();
                    } catch (Throwable th) {
                        this.i = j;
                        while (true) {
                            if (!atomicReference.compareAndSet(jl0Var, jl0Var2)) {
                                if (atomicReference.get() != jl0Var) {
                                    cn0.b("Unexpected state change from: " + jl0Var + " to: " + jl0Var2 + '.');
                                }
                            }
                        }
                        throw th;
                    }
                case 4:
                    rh.b("Recursive call to resume()");
                    throw new RuntimeException();
                case 5:
                    throw new IllegalStateException("Pausable composition is complete and apply() should be applied");
                case 6:
                    throw new IllegalStateException("The paused composition has been applied");
                default:
                    throw new RuntimeException();
            }
        } catch (Exception e) {
            atomicReference.set(jl0.e);
            throw e;
        }
    }
}
