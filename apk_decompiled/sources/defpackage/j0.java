package defpackage;

import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class j0 implements Future {
    public static final boolean h = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    public static final Logger i = Logger.getLogger(j0.class.getName());
    public static final o4 j;
    public static final Object k;
    public volatile Object e;
    public volatile f0 f;
    public volatile i0 g;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [o4] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    static {
        ?? r3;
        try {
            th = null;
            r3 = new g0(AtomicReferenceFieldUpdater.newUpdater(i0.class, Thread.class, "a"), AtomicReferenceFieldUpdater.newUpdater(i0.class, i0.class, "b"), AtomicReferenceFieldUpdater.newUpdater(j0.class, i0.class, "g"), AtomicReferenceFieldUpdater.newUpdater(j0.class, f0.class, "f"), AtomicReferenceFieldUpdater.newUpdater(j0.class, Object.class, "e"));
        } catch (Throwable th) {
            th = th;
            r3 = new Object();
        }
        j = r3;
        if (th != null) {
            i.log(Level.SEVERE, "SafeAtomicHelper is broken!", th);
        }
        k = new Object();
    }

    public static void b(j0 j0Var) {
        i0 i0Var;
        f0 f0Var;
        do {
            i0Var = j0Var.g;
        } while (!j.r(j0Var, i0Var, i0.c));
        while (i0Var != null) {
            Thread thread = i0Var.a;
            if (thread != null) {
                i0Var.a = null;
                LockSupport.unpark(thread);
            }
            i0Var = i0Var.b;
        }
        do {
            f0Var = j0Var.f;
        } while (!j.p(j0Var, f0Var));
        f0 f0Var2 = null;
        while (f0Var != null) {
            f0 f0Var3 = f0Var.a;
            f0Var.a = f0Var2;
            f0Var2 = f0Var;
            f0Var = f0Var3;
        }
        while (f0Var2 != null) {
            f0Var2 = f0Var2.a;
            try {
                throw null;
                break;
            } catch (RuntimeException e) {
                i.log(Level.SEVERE, "RuntimeException while executing runnable null with executor null", (Throwable) e);
            }
        }
    }

    public static Object c(Object obj) {
        if (!(obj instanceof d0)) {
            if (!(obj instanceof e0)) {
                if (obj == k) {
                    return null;
                }
                return obj;
            }
            throw new ExecutionException((Throwable) null);
        }
        Throwable th = ((d0) obj).a;
        CancellationException cancellationException = new CancellationException("Task was cancelled.");
        cancellationException.initCause(th);
        throw cancellationException;
    }

    public static Object d(j0 j0Var) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = j0Var.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    public final void a(StringBuilder sb) {
        String valueOf;
        try {
            Object d = d(this);
            sb.append("SUCCESS, result=[");
            if (d == this) {
                valueOf = "this future";
            } else {
                valueOf = String.valueOf(d);
            }
            sb.append(valueOf);
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e.getClass());
            sb.append(" thrown from get()]");
        } catch (ExecutionException e2) {
            sb.append("FAILURE, cause=[");
            sb.append(e2.getCause());
            sb.append("]");
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        d0 d0Var;
        Object obj = this.e;
        if (obj == null) {
            if (h) {
                d0Var = new d0(new CancellationException("Future.cancel() was called."), z);
            } else if (z) {
                d0Var = d0.b;
            } else {
                d0Var = d0.c;
            }
            if (j.q(this, obj, d0Var)) {
                b(this);
                return true;
            }
            return false;
        }
        return false;
    }

    public final void e(i0 i0Var) {
        i0Var.a = null;
        while (true) {
            i0 i0Var2 = this.g;
            if (i0Var2 != i0.c) {
                i0 i0Var3 = null;
                while (i0Var2 != null) {
                    i0 i0Var4 = i0Var2.b;
                    if (i0Var2.a != null) {
                        i0Var3 = i0Var2;
                    } else if (i0Var3 != null) {
                        i0Var3.b = i0Var4;
                        if (i0Var3.a == null) {
                            break;
                        }
                    } else if (!j.r(this, i0Var2, i0Var4)) {
                        break;
                    }
                    i0Var2 = i0Var4;
                }
                return;
            }
            return;
        }
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j2, TimeUnit timeUnit) {
        long j3;
        boolean z;
        i0 i0Var = i0.c;
        long nanos = timeUnit.toNanos(j2);
        if (!Thread.interrupted()) {
            Object obj = this.e;
            if (obj != null) {
                return c(obj);
            }
            if (nanos > 0) {
                j3 = System.nanoTime() + nanos;
            } else {
                j3 = 0;
            }
            if (nanos >= 1000) {
                i0 i0Var2 = this.g;
                if (i0Var2 != i0Var) {
                    i0 i0Var3 = new i0();
                    do {
                        o4 o4Var = j;
                        o4Var.U(i0Var3, i0Var2);
                        if (o4Var.r(this, i0Var2, i0Var3)) {
                            while (true) {
                                LockSupport.parkNanos(this, nanos);
                                if (!Thread.interrupted()) {
                                    Object obj2 = this.e;
                                    if (obj2 != null) {
                                        return c(obj2);
                                    }
                                    long nanoTime = j3 - System.nanoTime();
                                    if (nanoTime < 1000) {
                                        e(i0Var3);
                                        nanos = nanoTime;
                                        break;
                                    }
                                    nanos = nanoTime;
                                } else {
                                    e(i0Var3);
                                    throw new InterruptedException();
                                }
                            }
                        } else {
                            i0Var2 = this.g;
                        }
                    } while (i0Var2 != i0Var);
                }
                return c(this.e);
            }
            while (nanos > 0) {
                Object obj3 = this.e;
                if (obj3 != null) {
                    return c(obj3);
                }
                if (!Thread.interrupted()) {
                    nanos = j3 - System.nanoTime();
                } else {
                    throw new InterruptedException();
                }
            }
            String j0Var = toString();
            String obj4 = timeUnit.toString();
            Locale locale = Locale.ROOT;
            String lowerCase = obj4.toLowerCase(locale);
            String str = "Waited " + j2 + " " + timeUnit.toString().toLowerCase(locale);
            if (nanos + 1000 < 0) {
                String concat = str.concat(" (plus ");
                long j4 = -nanos;
                long convert = timeUnit.convert(j4, TimeUnit.NANOSECONDS);
                long nanos2 = j4 - timeUnit.toNanos(convert);
                if (convert != 0 && nanos2 <= 1000) {
                    z = false;
                } else {
                    z = true;
                }
                if (convert > 0) {
                    String str2 = concat + convert + " " + lowerCase;
                    if (z) {
                        str2 = str2.concat(",");
                    }
                    concat = str2.concat(" ");
                }
                if (z) {
                    concat = concat + nanos2 + " nanoseconds ";
                }
                str = concat.concat("delay)");
            }
            if (isDone()) {
                throw new TimeoutException(str.concat(" but future completed as timeout expired"));
            }
            throw new TimeoutException(str + " for " + j0Var);
        }
        throw new InterruptedException();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.e instanceof d0;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        if (this.e != null) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (this.e instanceof d0) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            a(sb);
        } else {
            try {
                if (this instanceof ScheduledFuture) {
                    str = "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
                } else {
                    str = null;
                }
            } catch (RuntimeException e) {
                str = "Exception thrown from implementation: " + e.getClass();
            }
            if (str != null && !str.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append("]");
            } else if (isDone()) {
                a(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // java.util.concurrent.Future
    public final Object get() {
        Object obj;
        i0 i0Var = i0.c;
        if (!Thread.interrupted()) {
            Object obj2 = this.e;
            if (obj2 != null) {
                return c(obj2);
            }
            i0 i0Var2 = this.g;
            if (i0Var2 != i0Var) {
                i0 i0Var3 = new i0();
                do {
                    o4 o4Var = j;
                    o4Var.U(i0Var3, i0Var2);
                    if (o4Var.r(this, i0Var2, i0Var3)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.e;
                            } else {
                                e(i0Var3);
                                throw new InterruptedException();
                            }
                        } while (obj == null);
                        return c(obj);
                    }
                    i0Var2 = this.g;
                } while (i0Var2 != i0Var);
            }
            return c(this.e);
        }
        throw new InterruptedException();
    }
}
