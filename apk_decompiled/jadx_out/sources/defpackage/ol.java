package defpackage;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ol extends sr implements Runnable {
    private static volatile Thread _thread;
    private static volatile int debugStatus;
    public static final ol p;
    public static final long q;

    /* JADX WARN: Type inference failed for: r0v0, types: [nr, ol, sr] */
    static {
        Long l;
        ?? srVar = new sr();
        p = srVar;
        srVar.u(false);
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000L);
        } catch (SecurityException unused) {
            l = 1000L;
        }
        q = TimeUnit.MILLISECONDS.toNanos(l.longValue());
    }

    @Override // defpackage.sr
    public final void B(Runnable runnable) {
        if (debugStatus != 4) {
            super.B(runnable);
            return;
        }
        throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
    }

    @Override // defpackage.sr
    public final Thread F() {
        Thread thread;
        Thread thread2 = _thread;
        if (thread2 == null) {
            synchronized (this) {
                thread = _thread;
                if (thread == null) {
                    thread = new Thread(this, "kotlinx.coroutines.DefaultExecutor");
                    _thread = thread;
                    thread.setContextClassLoader(p.getClass().getClassLoader());
                    thread.setDaemon(true);
                    thread.start();
                }
            }
            return thread;
        }
        return thread2;
    }

    @Override // defpackage.sr
    public final void H(long j, qr qrVar) {
        throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
    }

    public final synchronized void N() {
        boolean z;
        int i = debugStatus;
        if (i != 2 && i != 3) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            return;
        }
        debugStatus = 3;
        J();
        notifyAll();
    }

    @Override // defpackage.sr, defpackage.hm
    public final un d(long j, f21 f21Var, yj yjVar) {
        long j2 = 0;
        if (j > 0) {
            if (j >= 9223372036854L) {
                j2 = Long.MAX_VALUE;
            } else {
                j2 = 1000000 * j;
            }
        }
        if (j2 < 4611686018427387903L) {
            long nanoTime = System.nanoTime();
            pr prVar = new pr(j2 + nanoTime, f21Var);
            K(nanoTime, prVar);
            return prVar;
        }
        return sg0.e;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        boolean z2;
        boolean G;
        w11.a.set(this);
        try {
            synchronized (this) {
                int i = debugStatus;
                if (i != 2 && i != 3) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    if (!G) {
                        return;
                    } else {
                        return;
                    }
                }
                debugStatus = 1;
                notifyAll();
                long j = Long.MAX_VALUE;
                while (true) {
                    Thread.interrupted();
                    long v = v();
                    if (v == Long.MAX_VALUE) {
                        long nanoTime = System.nanoTime();
                        if (j == Long.MAX_VALUE) {
                            j = q + nanoTime;
                        }
                        long j2 = j - nanoTime;
                        if (j2 <= 0) {
                            _thread = null;
                            N();
                            if (!G()) {
                                F();
                                return;
                            }
                            return;
                        }
                        if (v > j2) {
                            v = j2;
                        }
                    } else {
                        j = Long.MAX_VALUE;
                    }
                    if (v > 0) {
                        int i2 = debugStatus;
                        if (i2 != 2 && i2 != 3) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        if (z2) {
                            _thread = null;
                            N();
                            if (!G()) {
                                F();
                                return;
                            }
                            return;
                        }
                        LockSupport.parkNanos(this, v);
                    }
                }
            }
        } finally {
            _thread = null;
            N();
            if (!G()) {
                F();
            }
        }
    }

    @Override // defpackage.sr, defpackage.nr
    public final void shutdown() {
        debugStatus = 4;
        super.shutdown();
    }

    @Override // defpackage.ak
    public final String toString() {
        return "DefaultExecutor";
    }
}
