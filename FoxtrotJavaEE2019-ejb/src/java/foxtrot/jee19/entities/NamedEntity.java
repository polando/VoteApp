package foxtrot.jee19.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "getNamedEntityByUuid",
        query = "SELECT e FROM NamedEntity e WHERE e.uuid = :uuid")
public abstract class NamedEntity implements Serializable {

    private static final long serialVersionUID = 1894892695959439681L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private long jpaVersion;

    @Column(unique = true, length = 36)
    private String uuid;

    private String name;

    public NamedEntity() {
        this(false);
    }

    public NamedEntity(boolean isNew) {
        if (isNew) {
            uuid = UUID.randomUUID().toString();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public long getJpaVersion() {
        return jpaVersion;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NamedEntity other = (NamedEntity) obj;
        if (other.uuid == null) {
            throw new IllegalStateException("other.uuid not set");
        }
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id + "/v" + jpaVersion;
    }
}
