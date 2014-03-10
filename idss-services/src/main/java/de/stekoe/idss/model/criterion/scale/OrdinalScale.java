/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.model.criterion.scale;

import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@PrimaryKeyJoinColumn(name = "ordinal_scale_id", referencedColumnName = "scale_id")
public class OrdinalScale extends Scale<OrdinalValue> implements Serializable {

}
